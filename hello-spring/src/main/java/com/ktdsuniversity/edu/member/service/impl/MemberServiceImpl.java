package com.ktdsuniversity.edu.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.common.util.SHAEncrypter;
import com.ktdsuniversity.edu.member.dao.MemberDao;
import com.ktdsuniversity.edu.member.service.MemberService;
import com.ktdsuniversity.edu.member.vo.MemberVO;
import com.ktdsuniversity.edu.member.vo.RequestMemberLoginVO;
import com.ktdsuniversity.edu.member.vo.RequestRegistMemberVO;

import jakarta.validation.Valid;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

	@Override
	public boolean createNewMember(RequestRegistMemberVO requestRegistMemberVO) {
		int emailCount = this.memberDao.selectMemberCountByEmail(requestRegistMemberVO.getEmail());
		if ( emailCount ==1 ) {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다");
		}
		
		// 비밀번호 암호화
		// SALT 발급
		String salt = SHAEncrypter.generateSalt();
		// SALT 를 이요해 비밀번호 암호화.
		String encryptedPassword = SHAEncrypter.getEncrypt(requestRegistMemberVO.getPassword(),salt);				
		
		requestRegistMemberVO.setPassword(encryptedPassword);
		requestRegistMemberVO.setSalt(salt);
		
		int insertCount = this.memberDao.insertNewMember(requestRegistMemberVO);
		return insertCount > 0 ;
	}

	@Override
	public int readMemberCountByEmail(String email) {
		return this.memberDao.selectMemberCountByEmail(email);
	}

	@Override
	public MemberVO readMember( RequestMemberLoginVO requestMemberLoginVO) {
		
		// 1. Email 을 이용해 회원의 정보를 조회
		MemberVO memberVO = this.memberDao.selectMemberByEmail(requestMemberLoginVO.getEmail());
		// 2. 조회된 회원이 없을 경우(null)는 Exception Throw
		if(memberVO == null) {
			throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
		}
		
		// 블락 케이스.
		// 1. 조회된 회원의 블락여부가 "Y"일 때, 무조건 예외를 던진다.
		// 2. 블락여부가 "Y"인 회원이 로그인 이 가능할 때
		//    마지막 블락 날짜에서 60분 후라면 로그인이 가능.
		if ( memberVO.getBlockYn().equals("Y")) {
			
			int count = this.memberDao.selectUnblockMemberByEmail(requestMemberLoginVO.getEmail());
			if ( count == 0 ) {
				throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");				
			}
			
		}
		
		// 3. 조회된 회원이 있을 경우 SALT 값만 가져옴
		String salt = memberVO.getSalt();
		// 4. SALT 를 이용해서 사용자가 입력한 비밀번호를 암호화
		String encryptedPassword = SHAEncrypter.getEncrypt(requestMemberLoginVO.getPassword(), salt);
		// 5. 암호화된 비밀번호와 조회한 회원의 비밀번호가 일치하는지 검사.
		if ( ! memberVO.getPassword().equals(encryptedPassword) ) {
			
			// 실패 트랜잭션
			// 1. 로그인 실패한 이메일의 실패횟수를 1증가시킨다.
			int updateCount = this.memberDao.updateLoginFailCountByEmail(requestMemberLoginVO.getEmail());
			
			// 2. 동시에 마지막 실패 날짜를 현재로 변경한다.
			
			// 3. 실패횟수가 지정횟수 이상이 되면 블락여부를 "Y" 로 변경한다.
			
			// 4. 동시에 마지막 블락 날짜를 현재로 변경한다.
			updateCount = this.memberDao.updateBlockByEmail(requestMemberLoginVO.getEmail());
			
			// 6. 일치하지 않을 경우는 Exception Throw
			throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");		
			
		}
		// 로그인 성공케이스
		// 1. 로그인 실패횟수를 0으로 초기화한다.
		// 2. 블락 여부를 "N" 으로 변경한다
		// 3. 마지막 로그인 성공날짜를 현재로 변경한다.
		int updateCount = this.memberDao.updateLoginSuccessByEmail(requestMemberLoginVO.getEmail());
		
		// 7. 일치할 경우 조회한 회원의 정보를 반환
		return memberVO;
		
	}

}












