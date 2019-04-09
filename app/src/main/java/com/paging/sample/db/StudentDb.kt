package com.paging.sample.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.paging.sample.ioThread

/**
 * Copyright (c), 2018-2019
 * @author: lixin
 * Date: 2019/4/9
 * Description:
 */
@Database(entities = [Student::class], version = 1)
abstract class StudentDb: RoomDatabase() {

    companion object {
        private var instance: StudentDb? = null

        @Synchronized
        fun get(context: Context): StudentDb {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    StudentDb::class.java, "StudentDb")
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            ioThread {
                                get(context).studentDao()
                                    .insert(CHEESE_DATA.map {
                                        Student(0, it)
                                    })
                            }
                        }
                    })
                    .build()

            }
            return instance!!
        }
    }

    abstract fun studentDao(): StudentDao
}

private val CHEESE_DATA = arrayListOf(
    "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
    "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag",
    "Airedale", "Aisy Cendre", "Allgauer Emmentaler", "Alverca", "Ambert",  // 15
    "American Cheese", "Ami du Chambertin", "Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro",
    "Appenzell", "Aragon", "Ardi Gasna", "Ardrahan", "Armenian String",
    "Aromes au Gene de Marc", "Asadero", "Asiago", "Aubisque Pyrenees", "Autun", // 30
    "Avaxtskyr", "Baby Swiss", "Babybel", "Baguette Laonnaise", "Bakers",
    "Baladi", "Balaton", "Bandal", "Banon", "Barry's Bay Cheddar", "Basing", "Basket Cheese", "Bath Cheese", "Bavarian Bergkase",
    "Baylough", "Beaufort", "Beauvoorde", "Beenleigh Blue", "Beer Cheese", "Bel Paese",
    "Bergader", "Bergere Bleue", "Berkswell", "Beyaz Peynir", "Bierkase", "Bishop Kennedy",
    "Blarney", "Bleu d'Auvergne", "Bleu de Gex", "Bleu de Laqueuille",
    "Bleu de Septmoncel", "Bleu Des Causses", "Blue", "Blue Castello", "Blue Rathgore",
    "Blue Vein (Australian)", "Blue Vein Cheeses", "Bocconcini", "Bocconcini (Australian)"
)