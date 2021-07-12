/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.petrealm.pets.repositories

import com.raywenderlich.android.petrealm.pets.models.Pet
import com.raywenderlich.android.petrealm.realm.PetRealm
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PetsRepositoryImpl @Inject constructor(
    private val config: RealmConfiguration,
) : PetsRepository {

  override fun addPet(
      name: String,
      age: Int,
      type: String,
      image: Int?
  ): Flow<PetDataStatus> = flow {
    emit(PetDataStatus.Loading)
    val realm = Realm.getInstance(config)
    realm.executeTransactionAwait {
      val pet = PetRealm(name = name, age = age, petType = type, image = image)
      it.insert(pet)
    }
    emit(PetDataStatus.Added)
  }.flowOn(Dispatchers.IO)

  override fun getPetsToAdopt(): Flow<PetDataStatus> = flow {
    emit(PetDataStatus.Loading)
    val realm = Realm.getInstance(config)
    val petsToAdopt = realm
        .where(PetRealm::class.java)
        .isEmpty("owner")
        .findAll()
        .map {
          Pet(
              name = it.name,
              age = it.age,
              image = it.image,
              petType = it.petType,
              isAdopted = false,
              id = it.id
          )
        }
    emit(PetDataStatus.Result(petsToAdopt))
  }.flowOn(Dispatchers.IO)

  override fun getAdoptedPets(): Flow<PetDataStatus> = flow {
    emit(PetDataStatus.Loading)
    val realm = Realm.getInstance(config)
    val petsToAdopt = realm
        .where(PetRealm::class.java)
        .isNotEmpty("owner")
        .findAll()
        .sort("name", Sort.ASCENDING)
        .map {
          val name = it.owner?.get(0)?.name
          Pet(
              name = it.name,
              age = it.age,
              image = it.image,
              petType = it.petType,
              id = it.id,
              isAdopted = true,
              ownerName = name
          )
        }
    emit(PetDataStatus.Result(petsToAdopt))
  }.flowOn(Dispatchers.IO)

  override fun updatePet(petRealm: PetRealm) {
    TODO("Not yet implemented")
  }

  override fun deletePet(petId: String): Flow<PetDataStatus> = flow {
    emit(PetDataStatus.Loading)
    val realm = Realm.getInstance(config)
    realm.executeTransactionAwait { realmTransaction ->
      val petToRemove = realmTransaction
          .where(PetRealm::class.java)
          .equalTo("id", petId)
          .findFirst()

      petToRemove?.deleteFromRealm()
    }

    emit(PetDataStatus.Deleted)
  }.flowOn(Dispatchers.IO)
}