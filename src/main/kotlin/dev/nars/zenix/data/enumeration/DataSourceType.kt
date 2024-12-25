package dev.nars.zenix.data.enumeration

enum class DataSourceType {
    MAIN,
    CONV,
    POST,
    ;

    fun generateLookUpKey(
        connectionName: String,
        shardIdx: Int?,
        slaveIdx: Int?
    ): String {
        return when (shardIdx == null) {
            true -> "m:${name.lowercase()}:$connectionName"
            else -> "s$slaveIdx:${name.lowercase()}:$connectionName-$shardIdx"
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