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

import com.raywenderlich.android.petrealm.realm.OwnerDatabaseOperations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OwnersRepositoryImpl @Inject constructor(
    private val databaseOperations: OwnerDatabaseOperations
) : OwnersRepository {

  override fun addOwner(name: String, image: Int?): Flow<OwnerDataStatus> = flow {
    emit(OwnerDataStatus.Loading)
    databaseOperations.insertOwner(name, image)
    emit(OwnerDataStatus.Added)
  }.flowOn(Dispatchers.IO)

  override fun updateOwner(ownerId: String, name: String, image: Int?): Flow<OwnerDataStatus> = flow {
    emit(OwnerDataStatus.Loading)
    databaseOperations.updateOwner(ownerId, name, image)
    emit(OwnerDataStatus.Updated)
  }.flowOn(Dispatchers.IO)

  override fun getOwners(): Flow<OwnerDataStatus> = flow {
    emit(OwnerDataStatus.Loading)
    val owners = databaseOperations.retrieveOwners()
    emit(OwnerDataStatus.AllOwnersRetrieved(owners))
  }.flowOn(Dispatchers.IO)

  override fun getOwner(ownerId: String): Flow<OwnerDataStatus> = flow {
    emit(OwnerDataStatus.Loading)
    val owner = databaseOperations.retrieveOwner(ownerId)
    owner?.let {
      emit(OwnerDataStatus.OwnerRetrieved(it))
    } ?: emit(OwnerDataStatus.OwnerNotFound)
  }.flowOn(Dispatchers.IO)

  override fun adoptPet(petId: String, ownerId: String): Flow<OwnerDataStatus> = flow {
    emit(OwnerDataStatus.Loading)
    databaseOperations.updatePets(petId, ownerId)
    emit(OwnerDataStatus.PetAdopted)
  }.flowOn(Dispatchers.IO)

  override fun deleteOwner(ownerId: String): Flow<OwnerDataStatus> = flow {
    emit(OwnerDataStatus.Loading)
    databaseOperations.removeOwner(ownerId)
    emit(OwnerDataStatus.Deleted)
  }.flowOn(Dispatchers.IO)
}