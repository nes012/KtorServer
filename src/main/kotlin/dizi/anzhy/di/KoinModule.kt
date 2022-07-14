package dizi.anzhy.di

import dizi.anzhy.repository.HeroRepository
import dizi.anzhy.repository.HeroRepositoryImpl
import org.koin.dsl.module

val koinModule = module {
    single<HeroRepository> {
        HeroRepositoryImpl()
    }
}