package common

sealed class Result {
    data class Number(val value: Int): Result()
}