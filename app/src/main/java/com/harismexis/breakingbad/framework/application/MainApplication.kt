package com.harismexis.breakingbad.framework.application

import android.util.Log
import com.harismexis.breakingbad.framework.di.DaggerMainComponent
import com.harismexis.breakingbad.framework.di.MainComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerApplication
import kotlinx.coroutines.*
import javax.inject.Inject

class MainApplication : DaggerApplication(), HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    /**
     * No need to cancel this scope as it'll be torn down with the process
     *
     * We don’t need to cancel this scope since we want it to remain active as long as the application process is
     * alive, so we don’t hold a reference to the SupervisorJob. We can use this scope to run coroutines that
     * need a longer lifetime than the calling scope might offer in our app.
     */
    val applicationScope = CoroutineScope(
        SupervisorJob()
                + Dispatchers.IO
                + CoroutineName("application scope for important operations")
                + CoroutineExceptionHandler { _, _ -> Log.d("", "error") }
    )

    private lateinit var component: MainComponent

    override fun androidInjector(): AndroidInjector<Any> {
        return injector
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        component = DaggerMainComponent.factory().create(this)
        component.inject(this)
        return component
    }

}