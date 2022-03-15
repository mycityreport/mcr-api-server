package info.mycityreport.api.core.ktor.usecase

@JvmInline
value class Resource(val value: String)

@JvmInline
value class Permission(val value: String) {
    init {
        val permissions = listOf("create", "read", "update", "delete")
        require(permissions.contains(value)) { "invalid permission format" }
    }
}

data class ResourcePermission(private val _resource: Resource, private val _permission: Permission) {
    val resource: String
        get() = _resource.value
    val permission: String
        get() = _permission.value
}
