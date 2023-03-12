package nicolas2lee.github.com.springbootlambda

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.function.Function

@SpringBootApplication
class SpringbootLambdaApplication{
	@Bean
	fun  uppercase() : Function<String, String>{
		return Function {
			return@Function it.uppercase();
		}
	}

}

fun main(args: Array<String>) {
	runApplication<SpringbootLambdaApplication>(*args)
}

