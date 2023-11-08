package kdt.dev.ecommerce.user.application;

import org.springframework.stereotype.Service;

import kdt.dev.ecommerce.global.exception.CommerceException;
import kdt.dev.ecommerce.user.domain.User;
import kdt.dev.ecommerce.user.domain.UserRepository;
import kdt.dev.ecommerce.user.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserFindService {

	private final UserRepository userRepository;

	public User getById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new CommerceException(UserErrorCode.NOT_FOUND));
	}
}
