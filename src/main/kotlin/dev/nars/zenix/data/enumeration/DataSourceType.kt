package dev.nars.zenix.data.enumeration

enum class DataSourceType {
    MAIN,
    CONV,
    POST,
    ;

    fun generateLookUpKey(
        connectionName: String,
        shardIdx: Int?,
    ): String {
        return when (shardIdx == null) {
            true -> "${name.lowercase()}:$connectionName"
            else -> "${name.lowercase()}:sh.$shardIdx:$connectionName"
        }
    }

    fun generatePoolName(
        slaveIdx: Int?
    ): String {
        return when (slaveIdx == null) {
            true -> "${name.lowercase()}MasterPool"
            else -> "${name.lowercase()}SlavePool-$slaveIdx"
        }
    }
}