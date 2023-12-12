package com.app.mega.service.jpa;

import com.app.mega.common.CommonResponse;
import com.app.mega.common.Constants;
import com.app.mega.common.handler.CustomException;
import com.app.mega.dto.request.user.AuthenticationRequest;
import com.app.mega.dto.response.user.AuthenticationResponse;
import com.app.mega.dto.request.user.CertificationRequest;
import com.app.mega.config.util.ses.EmailSender;
import com.app.mega.config.util.sns.SnsSender;
import com.app.mega.config.util.sns.SnsSenderDto;
import com.app.mega.entity.User;
import com.app.mega.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AuthenticationService{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailSender emailSender;
    private final SnsSender snsSender;

    private int certificationNumber;
    HashMap<Long, Integer> certification = new HashMap<>();

    //로그인
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // 인증 시도. 인증에 실패하면 AuthenticationError 반환됨
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 인증 성공 시 해당 User 생성
        User user = userRepository.findByEmail(request.getEmail());

        // 본인인증이 된 유저라면 토큰발급
        if(Boolean.TRUE.equals(user.getIsIdentified())) {
            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .isIdentified(user.getIsIdentified())
                    .id(user.getId())
                    .build();
        }
        // 본인인증이 안된 유저라면 토큰발급안함
        else {
            return AuthenticationResponse.builder()
                    .token(null)
                    .isIdentified(user.getIsIdentified())
                    .build();
        }
    }

    public ResponseEntity checkForIdentify(String email, String phone, String name, String identifyMethod) throws CustomException {
        User user = userRepository.findByEmailAndPhoneAndName(email, phone, name)
                .orElseThrow(() -> new CustomException(Constants.ExceptionType.AUTHENTICATION, HttpStatus.BAD_REQUEST, "등록된 사용자가 아닙니다."));

        Long userId = userRepository.findByEmail(email).getId();
        if(certification.get(userId) != null) {
            certification.remove(userId);
        }

        certificationNumber =  ThreadLocalRandom.current().nextInt(100000, 1000000);
        certification.put(userId, certificationNumber);

        //인증번호 문자 발송
        if(Objects.equals(identifyMethod, "phone")) {

            String mobileNo = "+82"+user.getPhone();
            String smsTxt = "MEGA 인증메시지입니다. 어플에 인증번호를 입력해주세요.\n인증번호: "+certificationNumber;

            SnsSenderDto dto = new SnsSenderDto(mobileNo, smsTxt);
            snsSender.send(dto);
            //PublishResult result = snsSender.send(dto);
            return ResponseEntity.status(HttpStatus.OK).body(
                    CommonResponse.builder()
                            .responseCode(1)
                            .responseMessage("[성공] 인증문자 전송 완료")
                            .data(user.getId())
                            .build());
        }
        //인증번호 메일 발송
        if(Objects.equals(identifyMethod, "email")) {
            ArrayList<String> to = new ArrayList<>();
            to.add(user.getEmail());
            String subject = "MEGA 인증메일입니다.";
            String content = "어플에 인증번호를 입력해주세요.\n인증번호: "+certificationNumber;

            emailSender.send(subject, content, to);

            return ResponseEntity.status(HttpStatus.OK).body(
                    CommonResponse.builder()
                            .responseCode(1)
                            .responseMessage("[성공] 인증메일 전송 완료")
                            .data(user.getId())
                            .build());

        }
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .responseCode(-1)
                        .responseMessage("[실패] 잘못된 인증수단 요청")
                        .data(null)
                        .build());
    }

    public ResponseEntity certificate(CertificationRequest request) throws CustomException {
        Long userId = request.getId();
        if(certification.get(userId) == request.getCertificationNumber()) {
            certification.remove(userId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    CommonResponse.builder()
                            .responseCode(1)
                            .responseMessage("[성공] 인증번호 일치")
                            .data(userId)
                            .build());
        } else {
            throw new CustomException(Constants.ExceptionType.AUTHENTICATION, HttpStatus.BAD_REQUEST, "[실패] 인증번호 불일치");
        }
    }

    @Transactional
    public ResponseEntity resetPassword(Long id, String rawPassword) throws CustomException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        Constants.ExceptionType.AUTHENTICATION, HttpStatus.BAD_REQUEST, "등록된 사용자가 아닙니다."));

        user.setIsIdentified(true);
        user.setPassword(passwordEncoder.encode(rawPassword));

        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .responseCode(1)
                        .responseMessage("[성공] 비밀번호 변경 완료")
                        .data(null)
                        .build());
    }
}
