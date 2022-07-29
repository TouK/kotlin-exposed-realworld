package io.realworld.shared

import net.datafaker.Faker
import java.util.Locale
import kotlin.math.pow

object Gen {
    private val faker = Faker(Locale.forLanguageTag("pl-PL"))

    fun id() = number(precision = 10)
    fun positiveInt(): Int = faker.number().numberBetween(0, Int.MAX_VALUE)
    fun positiveFloat(): Float = faker.number().randomDouble(2, 0, 100).toFloat()
    fun number(precision: Int = 10): Long = faker.number().numberBetween(0L, 10.0.pow(precision).toLong())
    fun alphanumeric(count: Int = 24): String = faker.lorem().characters(count, true)
    fun digits(count: Int = 9): String = faker.number().digits(count)

    fun email() = "${Gen.alphanumeric(count = 5)}@${Gen.alphanumeric(count = 10)}.pl"
}
