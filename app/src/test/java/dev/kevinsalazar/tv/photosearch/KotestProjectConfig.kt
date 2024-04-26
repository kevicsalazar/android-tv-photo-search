package dev.kevinsalazar.tv.photosearch

import io.kotest.core.config.AbstractProjectConfig

object KotestProjectConfig : AbstractProjectConfig() {
    override var coroutineTestScope = true
    override val coroutineDebugProbes = true
}
