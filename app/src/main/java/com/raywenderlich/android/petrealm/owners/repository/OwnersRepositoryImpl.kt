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

package com.raywenderlich.android.petrealm.owners.repository

import com.raywenderlich.android.petrealm.owners.models.Owner
import com.raywenderlich.android.petrealm.pets.models.Pet
import com.raywenderlich.android.petrealm.realm.OwnerRealm
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

class OwnersRepositoryImpl @Inject constructor(
    private val config: RealmConfiguration
) : OwnersRepository {

  override fun addOwner(name: String, image: Int?): Flow<Boolean> = flow {
    emit(false)
    val realm = Realm.getInstance(config)
    realm.executeTransactionAwait {
      val owner = OwnerRealm(name = name, image = image)
      it.insert(owner)
    }
    emit(true)
  }.flowOn(Dispatchers.IO)

  override fun getOwners(): Flow<List<Owner>> = flow {
    val realm = Realm.getInstance(config)
    val owners = realm
        .where(OwnerRealm::class.java)
        .findAll()
        .sort("name", Sort.ASCENDING)
        .map { owner ->
          val pets = owner.pets.map { pet ->
            Pet(
                id = pet.id,
                name = pet.name,
                age = pet.age,
                petType = pet.petType,
                image = pet.image,
                isAdopted = true
            )
          }
          val petCount = realm.where(PetRealm::class.java)
              .equalTo("owner.id", owner.id)
              .count()
          Owner(name = owner.name, image = owner.image, id = owner.id, pets = pets,
              numberOfPets = petCount)
        }
    emit(owners)
  }.flowOn(Dispatchers.IO)

  override fun adoptPet(petId: String, ownerId: String): Flow<Boolean> = flow {
    val realm = Realm.getInstance(config)

    realm.executeTransactionAwait { realmTransaction ->
      val pet = realmTransaction
          .where(PetRealm::class.java)
          .equalTo("id", petId)
          .findFirst()

      val owner = realmTransaction
          .where(OwnerRealm::class.java)
          .equalTo("id", ownerId)
          .findFirst()

      owner?.pets?.add(pet)
    }

    emit(true)
  }.flowOn(Dispatchers.IO)

  override fun deleteOwner(ownerId: String): Flow<Boolean> = flow {
    val realm = Realm.getInstance(config)

    realm.executeTransactionAwait { realmTransaction ->
      val ownerToRemove = realmTransaction
          .where(OwnerRealm::class.java)
          .equalTo("id", ownerId)
          .findFirst()

      ownerToRemove?.pets?.deleteAllFromRealm()
      ownerToRemove?.deleteFromRealm()
    }

    emit(true)
  }.flowOn(Dispatchers.IO)
}