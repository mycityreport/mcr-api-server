package info.mycityreport.api.healthcheck.usecase

import info.mycityreport.api.core.ktor.usecase.MCRException
import info.mycityreport.api.core.ktor.usecase.Permission
import info.mycityreport.api.core.ktor.usecase.Resource
import info.mycityreport.api.core.ktor.usecase.ResourcePermission
import info.mycityreport.api.healthcheck.domain.entities.HTTPSuccessStatusCode

class GetHealthStatusUseCaseWithAuth {
    fun execute(permissions: List<ResourcePermission>): HealthStatusDTO {
        val requiredPermission = ResourcePermission(Resource("problem_report"), Permission("read"))
        if (!permissions.contains(requiredPermission)) {
            throw MCRException.AuthorizationException
        }
        val status = HTTPSuccessStatusCode(200)
        return HealthStatusDTO(status)
    }
}
