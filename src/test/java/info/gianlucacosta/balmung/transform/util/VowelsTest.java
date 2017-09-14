/*^
  ===========================================================================
  Balmung
  ===========================================================================
  Copyright (C) 2017 Gianluca Costa
  ===========================================================================
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ===========================================================================
*/

package info.gianlucacosta.balmung.transform.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class VowelsTest {
    @Test
    public void isVowelShouldDetectPlainVowels() {
        Vowels
                .PLAIN_VOWELS
                .forEach(vowel ->
                        assertThat(
                                Vowels.isVowel(vowel),
                                is(true)
                        )
                );
    }


    @Test
    public void isVowelShouldDetectUmlautVowels() {
        Vowels
                .UMLAUT_VOWELS
                .forEach(vowel ->
                        assertThat(
                                Vowels.isVowel(vowel),
                                is(true)
                        )
                );
    }


    @Test
    public void isVowelShouldNotDetectConsonants() {
        assertThat(
                Vowels.isVowel('b'),
                is(false)
        );
    }


    @Test
    public void isPlainVowelShouldDetectPlainVowels() {
        Vowels
                .PLAIN_VOWELS
                .forEach(vowel ->
                        assertThat(
                                Vowels.isPlainVowel(vowel),
                                is(true)
                        )
                );
    }


    @Test
    public void isPlainVowelShouldNotDetectUmlautVowels() {
        Vowels
                .UMLAUT_VOWELS
                .forEach(vowel ->
                        assertThat(
                                Vowels.isPlainVowel(vowel),
                                is(false)
                        )
                );
    }


    @Test
    public void isPlainVowelShouldNotDetectConsonants() {
        assertThat(
                Vowels.isPlainVowel('b'),
                is(false)
        );
    }


    @Test
    public void isUmlautVowelShouldNotDetectPlainVowels() {
        Vowels
                .PLAIN_VOWELS
                .forEach(vowel ->
                        assertThat(
                                Vowels.isUmlautVowel(vowel),
                                is(false)
                        )
                );
    }


    @Test
    public void isUmlautVowelShouldDetectUmlautVowels() {
        Vowels
                .UMLAUT_VOWELS
                .forEach(vowel ->
                        assertThat(
                                Vowels.isUmlautVowel(vowel),
                                is(true)
                        )
                );
    }


    @Test
    public void isUmlautVowelShouldNotDetectConsonants() {
        assertThat(
                Vowels.isUmlautVowel('b'),
                is(false)
        );
    }


    @Test
    public void addUmlautShouldAddUmlautToPlainVowelsSupportingIt() {
        assertThat(
                Vowels.addUmlaut('A'),
                equalTo('Ä')
        );

        assertThat(
                Vowels.addUmlaut('a'),
                equalTo('ä')
        );

        assertThat(
                Vowels.addUmlaut('O'),
                equalTo('Ö')
        );

        assertThat(
                Vowels.addUmlaut('o'),
                equalTo('ö')
        );

        assertThat(
                Vowels.addUmlaut('U'),
                equalTo('Ü')
        );

        assertThat(
                Vowels.addUmlaut('u'),
                equalTo('ü')
        );
    }


    @Test
    public void addUmlautShouldNotAffectPlainVowelsNotSupportingIt() {
        assertThat(
                Vowels.addUmlaut('E'),
                equalTo('E')
        );

        assertThat(
                Vowels.addUmlaut('e'),
                equalTo('e')
        );

        assertThat(
                Vowels.addUmlaut('I'),
                equalTo('I')
        );

        assertThat(
                Vowels.addUmlaut('i'),
                equalTo('i')
        );
    }


    @Test
    public void addUmlautShouldNotAffectUmlautVowels() {
        Vowels.UMLAUT_VOWELS.forEach(vowel ->
                assertThat(
                        Vowels.addUmlaut(vowel),
                        equalTo(vowel)
                )
        );
    }


    @Test(expected = IllegalArgumentException.class)
    public void addUmlautShouldFailWhenAppliedToConsonants() {
        Vowels.addUmlaut('X');
    }


    @Test
    public void removeUmlautShouldRemoveItFromUmlautVowels() {
        assertThat(
                Vowels.removeUmlaut('Ä'),
                equalTo('A')
        );

        assertThat(
                Vowels.removeUmlaut('ä'),
                equalTo('a')
        );

        assertThat(
                Vowels.removeUmlaut('Ö'),
                equalTo('O')
        );

        assertThat(
                Vowels.removeUmlaut('ö'),
                equalTo('o')
        );

        assertThat(
                Vowels.removeUmlaut('Ü'),
                equalTo('U')
        );

        assertThat(
                Vowels.removeUmlaut('ü'),
                equalTo('u')
        );
    }


    @Test
    public void removeUmlautShouldNotAffectPlainVowels() {
        Vowels.PLAIN_VOWELS.forEach(vowel ->
                assertThat(
                        Vowels.removeUmlaut(vowel),
                        equalTo(vowel)
                )
        );
    }


    @Test(expected = IllegalArgumentException.class)
    public void removeUmlautShouldFailWhenAppliedToConsonants() {
        Vowels.removeUmlaut('X');
    }


    @Test
    public void isUmlautAddedShouldDetectUmlautPairs() {
        assertThat(
                Vowels.isUmlautAdded('A', 'Ä'),
                is(true)
        );

        assertThat(
                Vowels.isUmlautAdded('a', 'ä'),
                is(true)
        );

        assertThat(
                Vowels.isUmlautAdded('O', 'Ö'),
                is(true)
        );

        assertThat(
                Vowels.isUmlautAdded('o', 'ö'),
                is(true)
        );

        assertThat(
                Vowels.isUmlautAdded('U', 'Ü'),
                is(true)
        );

        assertThat(
                Vowels.isUmlautAdded('u', 'ü'),
                is(true)
        );
    }


    @Test
    public void isUmlautAddedShouldNotDetectReversedUmlautPairs() {
        assertThat(
                Vowels.isUmlautAdded('ä', 'a'),
                is(false)
        );
    }


    @Test
    public void isUmlautAddedShouldNotDetectNonUmlautVowels() {
        assertThat(
                Vowels.isUmlautAdded('E', 'E'),
                is(false)
        );

        assertThat(
                Vowels.isUmlautAdded('e', 'e'),
                is(false)
        );

        assertThat(
                Vowels.isUmlautAdded('I', 'I'),
                is(false)
        );

        assertThat(
                Vowels.isUmlautAdded('i', 'i'),
                is(false)
        );
    }


    @Test
    public void isUmlautAddedShouldNotDetectNonMatchingPairs() {
        assertThat(
                Vowels.isUmlautAdded('u', 'ä'),
                is(false)
        );
    }


    @Test
    public void isUmlautAddedShouldNotDetectPairsWithConsonants() {
        assertThat(
                Vowels.isUmlautAdded('x', 'ä'),
                is(false)
        );
    }


    @Test
    public void supportsUmlautShouldBeTrueForPlainVowelsSupportingIt() {
        assertThat(
                Vowels.canReceiveUmlaut('A'),
                is(true)
        );

        assertThat(
                Vowels.canReceiveUmlaut('a'),
                is(true)
        );

        assertThat(
                Vowels.canReceiveUmlaut('O'),
                is(true)
        );

        assertThat(
                Vowels.canReceiveUmlaut('o'),
                is(true)
        );

        assertThat(
                Vowels.canReceiveUmlaut('U'),
                is(true)
        );

        assertThat(
                Vowels.canReceiveUmlaut('u'),
                is(true)
        );
    }


    @Test
    public void supportsUmlautShouldBeFalseForPlainVowelsNotSupportingIt() {
        assertThat(
                Vowels.canReceiveUmlaut('E'),
                is(false)
        );

        assertThat(
                Vowels.canReceiveUmlaut('e'),
                is(false)
        );

        assertThat(
                Vowels.canReceiveUmlaut('I'),
                is(false)
        );

        assertThat(
                Vowels.canReceiveUmlaut('i'),
                is(false)
        );
    }


    @Test
    public void supportsUmlautShouldBeFalseForUmlautVowels() {
        Vowels.UMLAUT_VOWELS.forEach(vowel ->
                assertThat(
                        Vowels.canReceiveUmlaut(vowel),
                        is(false)
                )
        );
    }


    @Test
    public void supportsUmlautShouldBeFalseForConsonants() {
        assertThat(
                Vowels.canReceiveUmlaut('x'),
                is(false)
        );
    }
}

