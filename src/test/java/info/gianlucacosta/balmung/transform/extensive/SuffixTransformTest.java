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

package info.gianlucacosta.balmung.transform.extensive;

import info.gianlucacosta.balmung.transform.TransformTestBase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class SuffixTransformTest extends TransformTestBase<SuffixTransform> {
    public SuffixTransformTest() {
        super(SuffixTransform::compute);
    }


    @Test
    public void identityTransformShouldBeDetected() {
        testTransformRetrievalAndApplication(
                "Spiegel",
                "Spiegel",
                new SuffixTransform(
                        "",
                        false,
                        ""
                )
        );
    }

    @Test
    public void umlautOnlyTransformShouldBeDetected() {
        testTransformRetrievalAndApplication(
                "Mantel",
                "Mäntel",
                new SuffixTransform(
                        "",
                        true,
                        ""
                )
        );
    }


    @Test
    public void addingOnlyTransformShouldBeDetected() {
        testTransformRetrievalAndApplication(
                "Kino",
                "Kinos",
                new SuffixTransform(
                        "",
                        false,
                        "s"
                )
        );
    }


    @Test
    public void addingWithUmlautTransformShouldBeDetected() {
        testTransformRetrievalAndApplication(
                "Buch",
                "Bücher",
                new SuffixTransform(
                        "",
                        true,
                        "er"
                )
        );
    }


    @Test
    public void replacingOnlyTransformShouldBeDetected() {
        testTransformRetrievalAndApplication(
                "Aula",
                "Aulen",
                new SuffixTransform(
                        "a",
                        false,
                        "en"
                )
        );
    }


    @Test
    public void moreComplexReplacementsShouldBeDetected() {
        testTransformRetrievalAndApplication(
                "Atlas",
                "Atlanten",
                new SuffixTransform(
                        "s",
                        false,
                        "nten"
                )
        );
    }


    @Test
    public void replacingTransformsWithUmlautShouldBeDetected() {
        testTransformRetrievalAndApplication(
                "hoch",
                "höher",
                new SuffixTransform(
                        "ch",
                        true,
                        "her"
                )
        );
    }


    @Test
    public void esTsetShouldNotAlterTheResult() {
        testTransformRetrievalAndApplication(
                "Straßenlied",
                "Straßenlieder",

                new SuffixTransform(
                        "",
                        false,
                        "er"
                )
        );
    }


    @Test
    public void existingUmlautsShouldNotAlterTheResult() {
        testTransformRetrievalAndApplication(
                "Präsidiumsmitglied",
                "Präsidiumsmitglieder",

                new SuffixTransform(
                        "",
                        false,
                        "er"
                )
        );
    }


    @Test
    public void testEquality() {
        SuffixTransform transform =
                SuffixTransform.compute("Haus", "Häuser");

        SuffixTransform verySameTransform =
                SuffixTransform.compute("Haus", "Häuser");

        assertThat(
                verySameTransform,
                equalTo(transform)
        );
    }


    @Test
    public void testHashCode() {
        SuffixTransform transform =
                SuffixTransform.compute("Haus", "Häuser");

        SuffixTransform verySameTransform =
                SuffixTransform.compute("Haus", "Häuser");

        assertThat(
                verySameTransform.hashCode(),
                equalTo(transform.hashCode())
        );
    }


    @Test(expected = IllegalArgumentException.class)
    public void creatingExtensiveSuffixTransformsResultShorterThanOriginShouldFail() {
        SuffixTransform.compute("very, very, very long string", "short string");
    }


    @Test(expected = IllegalArgumentException.class)
    public void applyingTransformsToStringsShorterThanTheSuffixToRemoveShouldFail() {
        SuffixTransform suffixTransform =
                new SuffixTransform(
                        "um",
                        false,
                        "en");

        suffixTransform.apply("m");
    }


    @Test(expected = IllegalArgumentException.class)
    public void applyingTransformsToStringsNotHavingTheSuffixToRemoveShouldFail() {
        SuffixTransform suffixTransform =
                new SuffixTransform(
                        "um",
                        false,
                        "en");

        suffixTransform.apply("aula");
    }


    @Test
    public void toStringForidentityTransformShouldWork() {
        SuffixTransform suffixTransform =
                new SuffixTransform(
                        "",
                        false,
                        ""
                );

        assertThat(
                suffixTransform.toString(),
                equalTo("-")
        );
    }


    @Test
    public void toStringForUmlautOnlyTransformShouldWork() {
        SuffixTransform suffixTransform =
                new SuffixTransform(
                        "",
                        true,
                        ""
                );

        assertThat(
                suffixTransform.toString(),
                equalTo("⸚")
        );
    }


    @Test
    public void toStringForAddingOnlyTransformShouldWork() {
        SuffixTransform suffixTransform =
                new SuffixTransform(
                        "",
                        false,
                        "s"
                );

        assertThat(
                suffixTransform.toString(),
                equalTo("-s")
        );
    }


    @Test
    public void toStringForAddingWithUmlautTransformShouldWork() {
        SuffixTransform suffixTransform =
                new SuffixTransform(
                        "",
                        true,
                        "er"
                );

        assertThat(
                suffixTransform.toString(),
                equalTo("⸚er")
        );
    }


    @Test
    public void toStringForReplacingOnlyTransformShouldWork() {
        SuffixTransform suffixTransform =
                new SuffixTransform(
                        "a",
                        false,
                        "en"
                );

        assertThat(
                suffixTransform.toString(),
                equalTo("-a ⇒ -en")
        );
    }


    @Test
    public void toStringForMoreComplexReplacementsShouldWork() {
        SuffixTransform suffixTransform =
                new SuffixTransform(
                        "s",
                        false,
                        "nten"
                );

        assertThat(
                suffixTransform.toString(),
                equalTo("-s ⇒ -nten")
        );
    }


    @Test
    public void toStringForReplacingTransformsWithUmlautShouldWork() {
        SuffixTransform suffixTransform =
                new SuffixTransform(
                        "ch",
                        true,
                        "her"
                );

        assertThat(
                suffixTransform.toString(),
                equalTo("-ch ⇒ ⸚her")
        );
    }
}
