package info.mycityreport.api.redmineproxy.presentation

internal fun String.pathParser(): String {
    // Ktor で request.path() を取得すると `/proxy/{...}` という状態で取れるので
    // 前半部分の `/proxy` を切り取る処理を行う
    return this.replace(Regex("^/proxy"), "")
}
