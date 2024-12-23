package dev.nars.zenix.enumeration

enum class DataSourceType {
    MAIN,
    CONV,
    POST,
    ;

    fun generateSourceName(
        connectionName: String,
        shardIdx: Int?
    ): String {
        return when (shardIdx == null) {
            true -> "${name.lowercase()}:$connectionName"
            else -> "${name.lowercase()}:$connectionName-$shardIdx"
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