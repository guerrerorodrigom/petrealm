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

package com.raywenderlich.android.petrealm.pets.data

import androidx.annotation.StringRes
import com.raywenderlich.android.petrealm.R

enum class PetType(@StringRes val type: Int) {
  DOG(R.string.dog),
  CAT(R.string.cat),
  OCTOPUS(R.string.octopus),
  SQUIRREL(R.string.squirrel),
  SQUID(R.string.squid),
  PENGUIN(R.string.penguin),
  SEAL(R.string.seal),
  SNAKE(R.string.snake),
  FISH(R.string.fish),
  OWL(R.string.owl),
  CHAMELEON(R.string.chameleon),
  FOX(R.string.fox),
  PIG(R.string.pig),
  TIGER(R.string.tiger),
  COW(R.string.cow),
  WOLF(R.string.wolf),
  SKUNK(R.string.skunk),
  RAT(R.string.rat),
  PANDA(R.string.panda),
  MONKEY(R.string.monkey),
  FROG(R.string.frog),
  SPIDER(R.string.spider),
  DUCK(R.string.duck),
  BUG(R.string.bug),
  BEAR(R.string.bear),
  BIRD(R.string.bird)
}