package common

sealed class Result {
    data class Number(val value: Int): Result()
    data class Long(val value: kotlin.Long): Result()
}