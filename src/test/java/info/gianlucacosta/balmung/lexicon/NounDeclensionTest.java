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

import javafx.util.Pair;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class NounDeclensionTest {
    @Test
    public void testEquality() {
        String testSuffix = "Example";

        NounDeclension testDeclension = createTestNounDeclension(testSuffix);

        NounDeclension verySameDeclension = createTestNounDeclension(testSuffix);

        assertThat(
                verySameDeclension,
                equalTo(testDeclension)
        );
    }


    @Test
    public void testInequality() {
        String testSuffix = "Example";

        NounDeclension testDeclension = createTestNounDeclension(testSuffix);

        NounDeclension differentDeclension = createTestNounDeclension(testSuffix + "X");


        assertThat(
                differentDeclension,
                not(equalTo(testDeclension))
        );
    }


    @Test
    public void testHashCode() {
        String testSuffix = "Example";

        NounDeclension testDeclension = createTestNounDeclension(testSuffix);

        NounDeclension verySameDeclension = createTestNounDeclension(testSuffix);

        assertThat(
                verySameDeclension.hashCode(),
                equalTo(testDeclension.hashCode())
        );
    }


    @Test
    public void creatingFromEnumeratedExpressionsShouldWork() {
        NounDeclension nounDeclension =
                NounDeclension.createOption(
                        "S_NOM",
                        "S_AKK",
                        "S_DAT",
                        "S_GEN",

                        "P_NOM",
                        "P_AKK",
                        "P_DAT",
                        "P_GEN"
                ).get();

        testNounDeclension(
                nounDeclension,
                Optional.of("S_NOM"),
                Optional.of("S_AKK"),
                Optional.of("S_DAT"),
                Optional.of("S_GEN"),

                Optional.of("P_NOM"),
                Optional.of("P_AKK"),
                Optional.of("P_DAT"),
                Optional.of("P_GEN")
        );
    }


    @Test
    public void creatingSingularOnlyFromEnumeratedExpressionsShouldWork() {
        NounDeclension nounDeclension =
                NounDeclension.createOption(
                        "S_NOM",
                        "S_AKK",
                        "S_DAT",
                        "S_GEN",

                        "",
                        "",
                        "",
                        ""
                ).get();

        testNounDeclension(
                nounDeclension,
                Optional.of("S_NOM"),
                Optional.of("S_AKK"),
                Optional.of("S_DAT"),
                Optional.of("S_GEN"),

                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
    }


    @Test
    public void creatingSingularOnlyFromEnumeratedExpressionsHavingNullShouldWork() {
        NounDeclension nounDeclension =
                NounDeclension.createOption(
                        "S_NOM",
                        "S_AKK",
                        "S_DAT",
                        "S_GEN",

                        null,
                        null,
                        null,
                        null
                ).get();

        testNounDeclension(
                nounDeclension,
                Optional.of("S_NOM"),
                Optional.of("S_AKK"),
                Optional.of("S_DAT"),
                Optional.of("S_GEN"),

                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
    }


    @Test
    public void creatingPluralOnlyFromEnumeratedExpressionsShouldWork() {
        NounDeclension nounDeclension =
                NounDeclension.createOption(
                        "",
                        "",
                        "",
                        "",
                        "P_NOM",
                        "P_AKK",
                        "P_DAT",
                        "P_GEN"
                ).get();

        testNounDeclension(
                nounDeclension,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),

                Optional.of("P_NOM"),
                Optional.of("P_AKK"),
                Optional.of("P_DAT"),
                Optional.of("P_GEN")
        );
    }


    @Test
    public void creatingDeclensionsWithNoEntryShouldReturnEmpty() {
        Optional<NounDeclension> nounDeclensionOption =
                NounDeclension.createOption(
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        ""
                );

        assertThat(
                nounDeclensionOption,
                equalTo(Optional.empty())
        );
    }


    @Test
    public void toStringShouldWorkWithFullDeclension() {
        NounDeclension nounDeclension =
                NounDeclension.createOption(
                        "S_NOM",
                        "S_AKK",
                        "S_DAT",
                        "S_GEN",

                        "P_NOM",
                        "P_AKK",
                        "P_DAT",
                        "P_GEN"
                ).get();

        assertThat(
                nounDeclension.toString(),
                equalTo(
                        "[[S_NOM, S_AKK, S_DAT, S_GEN], [P_NOM, P_AKK, P_DAT, P_GEN]]"
                )
        );
    }


    @Test
    public void toStringShouldWorkWithSingularOnlyDeclension() {
        NounDeclension nounDeclension =
                NounDeclension.createOption(
                        "S_NOM",
                        "S_AKK",
                        "S_DAT",
                        "S_GEN",

                        "",
                        "",
                        "",
                        ""
                ).get();

        assertThat(
                nounDeclension.toString(),
                equalTo(
                        "[[S_NOM, S_AKK, S_DAT, S_GEN], •]"
                )
        );
    }


    @Test
    public void toStringShouldWorkWithSingularOnlyDeclensionHavingNulls() {
        NounDeclension nounDeclension =
                NounDeclension.createOption(
                        "S_NOM",
                        "S_AKK",
                        "S_DAT",
                        "S_GEN",

                        null,
                        null,
                        null,
                        null
                ).get();

        assertThat(
                nounDeclension.toString(),
                equalTo(
                        "[[S_NOM, S_AKK, S_DAT, S_GEN], •]"
                )
        );
    }


    @Test
    public void toStringShouldWorkWithPluralOnlyDeclension() {
        NounDeclension nounDeclension =
                NounDeclension.createOption(
                        "",
                        "",
                        "",
                        "",

                        "P_NOM",
                        "P_AKK",
                        "P_DAT",
                        "P_GEN"
                ).get();

        assertThat(
                nounDeclension.toString(),
                equalTo(
                        "[•, [P_NOM, P_AKK, P_DAT, P_GEN]]"
                )
        );
    }


    @Test
    public void toStringShouldWorkWithMissingElements() {
        NounDeclension nounDeclension =
                NounDeclension.createOption(
                        "S_NOM",
                        "",
                        "S_DAT",
                        "S_GEN",

                        "",
                        "P_AKK",
                        "",
                        "P_GEN"
                ).get();

        assertThat(
                nounDeclension.toString(),
                equalTo(
                        "[[S_NOM, —, S_DAT, S_GEN], [—, P_AKK, —, P_GEN]]"
                )
        );
    }


    public static NounDeclension createTestNounDeclension(String testSuffix) {
        Map<Numerus, Map<Kasus, String>> initializingMap =
                Arrays
                        .stream(Numerus.values())
                        .map(numerus -> {
                            Map<Kasus, String> numerusMap =
                                    Arrays
                                            .stream(Kasus.values())
                                            .map(kasus -> {
                                                String testExpression =
                                                        String.format("%s_%s_%s",
                                                                numerus,
                                                                kasus,
                                                                testSuffix);

                                                return new Pair<>(kasus, testExpression);
                                            })
                                            .collect(
                                                    Collectors.toMap(
                                                            Pair::getKey,
                                                            Pair::getValue
                                                    )
                                            );

                            return new Pair<>(numerus, numerusMap);
                        })
                        .collect(
                                Collectors.toMap(
                                        Pair::getKey,
                                        Pair::getValue
                                )
                        );


        return NounDeclension.createOption(initializingMap).get();
    }


    private static void testNounDeclension(
            NounDeclension nounDeclension,

            Optional<String> singularNominative,
            Optional<String> singularAccusative,
            Optional<String> singularDative,
            Optional<String> singularGenitive,

            Optional<String> pluralNominative,
            Optional<String> pluralAccusative,
            Optional<String> pluralDative,
            Optional<String> pluralGenitive
    ) {
        assertThat(
                nounDeclension.getExpression(Numerus.SINGULAR, Kasus.NOMINATIV),
                equalTo(singularNominative)
        );

        assertThat(
                nounDeclension.getExpression(Numerus.SINGULAR, Kasus.AKKUSATIV),
                equalTo(singularAccusative)
        );

        assertThat(
                nounDeclension.getExpression(Numerus.SINGULAR, Kasus.DATIV),
                equalTo(singularDative)
        );

        assertThat(
                nounDeclension.getExpression(Numerus.SINGULAR, Kasus.GENITIV),
                equalTo(singularGenitive)
        );

        assertThat(
                nounDeclension.getExpression(Numerus.PLURAL, Kasus.NOMINATIV),
                equalTo(pluralNominative)
        );

        assertThat(
                nounDeclension.getExpression(Numerus.PLURAL, Kasus.AKKUSATIV),
                equalTo(pluralAccusative)
        );

        assertThat(
                nounDeclension.getExpression(Numerus.PLURAL, Kasus.DATIV),
                equalTo(pluralDative)
        );

        assertThat(
                nounDeclension.getExpression(Numerus.PLURAL, Kasus.GENITIV),
                equalTo(pluralGenitive)
        );
    }
}
