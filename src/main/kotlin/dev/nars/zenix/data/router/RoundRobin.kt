package dev.nars.zenix.data.router

import java.util.concurrent.atomic.AtomicInteger

class RoundRobin<T>(
    private val list: List<T>
) {

    private val counter = AtomicInteger(0)

    fun next(): T {
        val idx = counter.getAndUpdate { (it + 1) % list.size }
        return list[idx]
    }

    fun isNotEmpty(): Boolean {
        return list.isNotEmpty()
    }

}