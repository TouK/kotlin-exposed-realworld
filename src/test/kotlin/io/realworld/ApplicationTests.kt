package io.realworld

import io.realworld.shared.BaseDatabaseTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ApplicationTests : BaseDatabaseTest()  {

	@Test
	fun contextLoads() {}

}
