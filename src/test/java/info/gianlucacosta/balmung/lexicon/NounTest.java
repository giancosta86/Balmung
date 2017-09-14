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

package info.gianlucacosta.balmung.lexicon;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class NounTest {
    @Test
    public void testEquality() {
        String mainDeclensionTestSuffix = "main";
        String alternativeDeclensionTestSuffix = "alternative";

        Noun noun = new Noun(
                "Expression",
                Collections.singleton("Sample Category"),
                Arrays.asList("Syllable 1", "Syllable 2", "Syllable 3", "Syllable 4"),
                Optional.of("Pronunciation"),
                Collections.singleton("Synonym"),
                Collections.singleton("Antonym"),
                Collections.singleton("Hypernym"),
                Optional.of(Genus.NEUTRAL),
                NounDeclensionTest.createTestNounDeclension(mainDeclensionTestSuffix),
                Optional.of(NounDeclensionTest.createTestNounDeclension(alternativeDeclensionTestSuffix))
        );


        Noun verySameNoun = new Noun(
                "Expression",
                Collections.singleton("Sample Category"),
                Arrays.asList("Syllable 1", "Syllable 2", "Syllable 3", "Syllable 4"),
                Optional.of("Pronunciation"),
                Collections.singleton("Synonym"),
                Collections.singleton("Antonym"),
                Collections.singleton("Hypernym"),
                Optional.of(Genus.NEUTRAL),
                NounDeclensionTest.createTestNounDeclension(mainDeclensionTestSuffix),
                Optional.of(NounDeclensionTest.createTestNounDeclension(alternativeDeclensionTestSuffix))
        );


        assertThat(
                verySameNoun,
                equalTo(noun)
        );
    }


    @Test
    public void testInequality() {
        String mainDeclensionTestSuffix = "main";
        String alternativeDeclensionTestSuffix = "alternative";

        Noun noun = new Noun(
                "Expression",
                Collections.singleton("Sample Category"),
                Arrays.asList("Syllable 1", "Syllable 2", "Syllable 3", "Syllable 4"),
                Optional.of("Pronunciation"),
                Collections.singleton("Synonym"),
                Collections.singleton("Antonym"),
                Collections.singleton("Hypernym"),
                Optional.of(Genus.NEUTRAL),
                NounDeclensionTest.createTestNounDeclension(mainDeclensionTestSuffix),
                Optional.of(NounDeclensionTest.createTestNounDeclension(alternativeDeclensionTestSuffix))
        );


        Noun differentNoun = new Noun(
                "Expression",
                Collections.singleton("Sample Category"),
                Arrays.asList("Syllable 1", "Syllable 2", "Syllable 3", "Syllable 4"),
                Optional.of("Pronunciation"),
                Collections.singleton("Synonym"),
                Collections.singleton("RANDOM VALUE"), //This field changed
                Collections.singleton("Hypernym"),
                Optional.of(Genus.NEUTRAL),
                NounDeclensionTest.createTestNounDeclension(mainDeclensionTestSuffix),
                Optional.of(NounDeclensionTest.createTestNounDeclension(alternativeDeclensionTestSuffix))
        );


        assertThat(
                differentNoun,
                not(equalTo(noun))
        );
    }


    @Test
    public void testHashCode() {
        String mainDeclensionTestSuffix = "main";
        String alternativeDeclensionTestSuffix = "alternative";

        Noun noun = new Noun(
                "Expression",
                Collections.singleton("Sample Category"),
                Arrays.asList("Syllable 1", "Syllable 2", "Syllable 3", "Syllable 4"),
                Optional.of("Pronunciation"),
                Collections.singleton("Synonym"),
                Collections.singleton("Antonym"),
                Collections.singleton("Hypernym"),
                Optional.of(Genus.NEUTRAL),
                NounDeclensionTest.createTestNounDeclension(mainDeclensionTestSuffix),
                Optional.of(NounDeclensionTest.createTestNounDeclension(alternativeDeclensionTestSuffix))
        );


        Noun verySameNoun = new Noun(
                "Expression",
                Collections.singleton("Sample Category"),
                Arrays.asList("Syllable 1", "Syllable 2", "Syllable 3", "Syllable 4"),
                Optional.of("Pronunciation"),
                Collections.singleton("Synonym"),
                Collections.singleton("Antonym"),
                Collections.singleton("Hypernym"),
                Optional.of(Genus.NEUTRAL),
                NounDeclensionTest.createTestNounDeclension(mainDeclensionTestSuffix),
                Optional.of(NounDeclensionTest.createTestNounDeclension(alternativeDeclensionTestSuffix))
        );


        assertThat(
                verySameNoun.hashCode(),
                equalTo(noun.hashCode())
        );
    }
}
