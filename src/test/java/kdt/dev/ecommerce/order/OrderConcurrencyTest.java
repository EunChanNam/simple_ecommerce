package kdt.dev.ecommerce.order;

import static kdt.dev.ecommerce.order.application.usecase.OrderUseCase.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import kdt.dev.ecommerce.order.application.usecase.OrderUseCase;
import kdt.dev.ecommerce.order.presentation.dto.OrderRequest;
import kdt.dev.ecommerce.order.presentation.utils.OrderMapper;
import kdt.dev.ecommerce.user.domain.UserRepository;
import kdt.dev.ecommerce.user.fixture.UserFixture;

@SpringBootTest
@Sql("/sql/concurrency-test-dummy.sql")
@DisplayName("[주문 동시성 테스트]")
public class OrderConcurrencyTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderUseCase orderUseCase;

	@Test
	@DisplayName("[수량이 50개 있는 상품에 대해서 1개 주문 요청이 동시에 100개 오면 50개의 요청은 실패한다]")
	void test() throws InterruptedException {
		//given
		int threadCount = 100;
		ExecutorService service = Executors.newFixedThreadPool(threadCount);
		CountDownLatch latch = new CountDownLatch(threadCount);

		Long userId = userRepository.save(UserFixture.getUser()).getId();

		OrderRequest orderRequest = new OrderRequest(1L, List.of(1L), 1);
		OrderCommand orderCommand = OrderMapper.toOrderCommand(orderRequest, userId);

		Count failCount = new Count();

		//when
		for (int i = 0; i < threadCount; i++) {
			service.execute(() -> {
				try {
					orderUseCase.order(orderCommand);
				} catch (Exception e) {
					failCount.plus();
				}
				latch.countDown();
			});
		}

		latch.await();

		//then
		assertThat(failCount.count).isEqualTo(50);
	}

	static class Count {
		int count = 0;

		public void plus() {
			this.count++;
		}
	}
}
