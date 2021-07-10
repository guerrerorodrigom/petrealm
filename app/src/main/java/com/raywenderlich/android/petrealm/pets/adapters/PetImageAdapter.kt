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

package com.raywenderlich.android.petrealm.pets.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.petrealm.R
import com.raywenderlich.android.petrealm.databinding.ItemImageBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PetImageAdapter @Inject constructor() :
    RecyclerView.Adapter<PetImageAdapter.PetImageViewHolder>() {

  private val petImages = mutableListOf<Int>()
  private var tracker: SelectionTracker<Long>? = null

  init {
    initImages()
    setHasStableIds(true)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetImageViewHolder {
    val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return PetImageViewHolder(binding)
  }

  override fun onBindViewHolder(holder: PetImageViewHolder, position: Int) {
    val image = petImages[position]
    holder.bind(image, tracker?.isSelected(position.toLong()) ?: false)
  }

  override fun getItemCount() = petImages.size

  override fun getItemId(position: Int) = position.toLong()

  fun setTracker(tracker: SelectionTracker<Long>) {
    this.tracker = tracker
  }

  private fun initImages() {
    petImages.add(R.drawable.bear)
    petImages.add(R.drawable.bird)
    petImages.add(R.drawable.bug)
    petImages.add(R.drawable.cat01)
    petImages.add(R.drawable.chameleon)
    petImages.add(R.drawable.cow)
    petImages.add(R.drawable.dog01)
    petImages.add(R.drawable.duck)
    petImages.add(R.drawable.flying_fish)
    petImages.add(R.drawable.fox)
    petImages.add(R.drawable.frog)
    petImages.add(R.drawable.monkey)
    petImages.add(R.drawable.octopus)
    petImages.add(R.drawable.owl)
    petImages.add(R.drawable.panda)
    petImages.add(R.drawable.penguin)
    petImages.add(R.drawable.pig)
    petImages.add(R.drawable.rat)
    petImages.add(R.drawable.seal)
    petImages.add(R.drawable.skunk)
    petImages.add(R.drawable.snake)
    petImages.add(R.drawable.spider)
    petImages.add(R.drawable.squid)
    petImages.add(R.drawable.squirrel)
    petImages.add(R.drawable.tiger)
    petImages.add(R.drawable.wolf)
  }

  fun getSelectedImage(position: Long): Int {
    return petImages[position.toInt()]
  }

  class PetImageViewHolder(private val binding: ItemImageBinding) :
      RecyclerView.ViewHolder(binding.root) {

    fun bind(@DrawableRes image: Int, isSelected: Boolean) {
      with(binding) {
        petImage.setImageResource(image)
        selectedPet.isVisible = isSelected
      }
    }

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> = object :
        ItemDetailsLookup.ItemDetails<Long>() {
      override fun getPosition() = adapterPosition

      override fun getSelectionKey() = itemId
    }
  }
}