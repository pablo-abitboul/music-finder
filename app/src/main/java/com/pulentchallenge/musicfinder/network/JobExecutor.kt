package com.pulentchallenge.musicfinder.network

import java.util.concurrent.*


class JobExecutor : Executor {

    private var workQueue: BlockingQueue<Runnable>? = null

    private var threadPoolExecutor: ThreadPoolExecutor? = null

    private var threadFactory: ThreadFactory? = null

    init {
        workQueue = LinkedBlockingQueue()
        threadFactory = JobThreadFactory()
        threadPoolExecutor = ThreadPoolExecutor(
            INITIAL_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            workQueue
        )
    }

    override fun execute(runnable: Runnable?) {
        this.threadPoolExecutor?.execute(runnable);
    }


    private class JobThreadFactory : ThreadFactory {
        private val counter = 0
        override fun newThread(runnable: Runnable): Thread {
            val thread =
                Thread(runnable, THREAD_NAME + counter)
            thread.priority = Thread.MIN_PRIORITY
            return thread
        }

        companion object {
            private const val THREAD_NAME = "android_"
        }
    }

    companion object {
        // Sets the amount of time an idle thread waits before terminating
        private const val KEEP_ALIVE_TIME = 10L
        private const val INITIAL_POOL_SIZE = 3
        private const val MAX_POOL_SIZE = 5
        // Sets the Time Unit to seconds
        private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    }
}