package projectbuildup.saver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.saver.domain.auth.basic.dto.SignupRequestDto;
import projectbuildup.saver.domain.image.dto.ImageDto;
import projectbuildup.saver.domain.image.entity.Image;
import projectbuildup.saver.domain.image.repository.ImageRepository;
import projectbuildup.saver.domain.image.service.ImageService;
import projectbuildup.saver.domain.user.dto.PasswordUpdateParam;
import projectbuildup.saver.domain.user.dto.ProfileUpdateParam;
import projectbuildup.saver.domain.user.dto.UserIdRequestParam;
import projectbuildup.saver.domain.user.dto.UserProfileResponseDto;
import projectbuildup.saver.domain.user.entity.Member;
import projectbuildup.saver.domain.user.repository.MemberJpaRepository;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;
    private final UserFindService userFindService;
    private final PasswordEncoder passwordEncoder;

    private final ImageService imageService;
    private final ImageRepository imageRepository;

    /**
     * 사용자의 비밀번호를 변경합니다.
     * @param passwordUpdateParam 변경할 유저 아이디, 변경할 비밀번호
     */
    public void changePassword(PasswordUpdateParam passwordUpdateParam) {
        Member member = userFindService.findByIdToken(passwordUpdateParam.getIdToken());
        member.setPassword(passwordEncoder.encode(passwordUpdateParam.getPassword()));
        memberJpaRepository.save(member);
    }


    /**
     * 사용자의 프로필을 조회합니다.
     * @param memberId 조회할 회원의 아이디넘버
     * @return 유저 프로필(닉네임, 프로필 사진 URL)
     */
    public UserProfileResponseDto getProfile(Long memberId) {
        Member member = userFindService.findById(memberId);
        return new UserProfileResponseDto(member);
    }


    /**
     * 사용자의 프로필을 수정합니다.
     * @param memberId 수정할 회원의 아이디넘버
     * @param profileUpdateParam 수정 항목
     * @return 수정한 회원의 아이디넘버
     */
    public Long updateProfile(Long memberId, ProfileUpdateParam profileUpdateParam) {
        Member member = userFindService.findById(memberId);
        member.setNickName(profileUpdateParam.getNickName());
        memberJpaRepository.save(member);
        return member.getId();
    }


    /**
     * 프로필 사진을 변경합니다.
     * @param memberId 사용자 아이디넘버
     * @param imageFile 변경할 사진 파일
     * @return 변경한 사용자의 아이디
     */
    public Long changeProfileImage(Long memberId, MultipartFile imageFile) {
        Member member = userFindService.findById(memberId);
        ImageDto imageDto = imageService.uploadImage(imageFile);
        Image image = imageDto.toEntity();
        imageRepository.save(image);
        imageService.removeImage(member.getProfileImage());
        member.setProfileImage(image);
        memberJpaRepository.save(member);

        return member.getId();
    }

    /**
     * 회원가입 시, 회원을 생성합니다.
     * @param signupRequestDto 회원가입 시 받는 필드 (아이디토큰, 비밀번호, 닉네임, 전화번호)
     * @return 유저 아이디
     */
    public Long createUserBySignUp(SignupRequestDto signupRequestDto){
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        Member member = signupRequestDto.toEntity(encodedPassword);
        return memberJpaRepository.save(member).getId();
    }

    /** 회원을 삭제합니다.
     * @param userId 유저 아이디
     */
    public void deleteById(Long userId){
        memberJpaRepository.deleteById(userId);
    }
}
