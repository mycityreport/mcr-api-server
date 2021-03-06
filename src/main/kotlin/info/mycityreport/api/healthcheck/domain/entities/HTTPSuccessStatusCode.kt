package info.mycityreport.api.healthcheck.domain.entities

@JvmInline
value class HTTPSuccessStatusCode(val value: Int) {
    init {
        require(value == 200) { "HTTP Status must be 200" }
    }
}
