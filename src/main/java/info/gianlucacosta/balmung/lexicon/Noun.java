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

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "Nouns")
public class Noun extends Lemma {
    /**
     * Creates a key to access an expression in either the main or the alternative declension.
     * <p>
     * To simplify storage, both declensions are actually kept in a single map - whose key is
     * defined by the element's (main|alternative declension, numerus, kasus) coordinate.
     *
     * @param isMainDeclension true if the element to access is in the main declension, false for the alternative
     * @param numerus          The numerus
     * @param kasus            The casus
     * @return The ley to access the internal declension map
     */
    private static int buildDeclensionKey(boolean isMainDeclension, Numerus numerus, Kasus kasus) {
        return (isMainDeclension ? 0 : 8) + (4 * numerus.ordinal()) + kasus.ordinal();
    }


    private Genus genus;

    @ElementCollection
    @MapKeyColumn(
            name = "key"
    )
    @Column(
            name = "expression",
            nullable = false
    )
    @CollectionTable(
            name = "Noun_Declension",
            joinColumns = {
                    @JoinColumn(
                            name = "lemmaExpression",
                            referencedColumnName = "expression"
                    ),

                    @JoinColumn(
                            name = "lemmaCategories",
                            referencedColumnName = "categories"
                    )
            },
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"lemmaExpression", "lemmaCategories", "key", "expression"}
            )
    )
    private Map<Integer, String> declensions;

    private transient NounDeclension declension;
    private transient Optional<NounDeclension> alternativeDeclensionOption;


    Noun() {
    }


    public Noun(
            String expression,
            Set<String> categories,
            NounDeclension declension
    ) {
        this(
                expression,
                categories,
                Collections.emptyList(),
                Optional.empty(),
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet(),
                Optional.empty(),
                declension,
                Optional.empty()
        );
    }


    public Noun(
            String expression,
            Set<String> categories,
            List<String> syllables,
            Optional<String> pronunciationOption,
            Set<String> synonyms,
            Set<String> antonyms,
            Set<String> hypernyms,
            Optional<Genus> genusOption,
            NounDeclension declension,
            Optional<NounDeclension> alternativeDeclensionOption
    ) {
        super(expression, categories, syllables, pronunciationOption, synonyms, antonyms, hypernyms);

        Objects.requireNonNull(genusOption);
        Objects.requireNonNull(declension);
        Objects.requireNonNull(alternativeDeclensionOption);

        this.genus = genusOption.orElse(null);
        this.declensions = new HashMap<>();

        storeDeclensionToInternalMap(
                Optional.of(declension),
                true
        );

        storeDeclensionToInternalMap(
                alternativeDeclensionOption,
                false
        );
    }


    private void storeDeclensionToInternalMap(Optional<NounDeclension> declensionToStoreOption, boolean isMainDeclension) {
        declensionToStoreOption.ifPresent(declensionToStore ->
                Arrays
                        .stream(Numerus.values())
                        .forEach(numerus ->
                                Arrays
                                        .stream(Kasus.values())
                                        .forEach(kasus -> {
                                            Optional<String> expressionOption =
                                                    declensionToStore.getExpression(numerus, kasus);

                                            expressionOption.ifPresent(expression -> {
                                                int key =
                                                        buildDeclensionKey(isMainDeclension, numerus, kasus);

                                                declensions.put(key, expression);
                                            });
                                        })));
    }


    public Optional<Genus> getGenus() {
        return Optional.ofNullable(genus);
    }


    public NounDeclension getDeclension() {
        if (declension == null) {
            declension = initializeDeclensionFromInternalMap(true).get();
        }

        return declension;
    }


    public Optional<NounDeclension> getAlternativeDeclension() {
        if (alternativeDeclensionOption == null) {
            alternativeDeclensionOption = initializeDeclensionFromInternalMap(false);
        }

        return alternativeDeclensionOption;
    }


    private Optional<NounDeclension> initializeDeclensionFromInternalMap(boolean isMainDeclension) {
        Map<Numerus, Map<Kasus, String>> declensionMap = new HashMap<>();

        Arrays
                .stream(Numerus.values())
                .forEach(numerus -> {
                    Map<Kasus, String> numerusMap = new HashMap<>();

                    Arrays
                            .stream(Kasus.values())
                            .forEach(kasus -> {
                                int key =
                                        buildDeclensionKey(isMainDeclension, numerus, kasus);

                                String expression =
                                        declensions.get(key);

                                if (expression != null) {
                                    numerusMap.put(kasus, expression);
                                }
                            });

                    if (!numerusMap.isEmpty()) {
                        declensionMap.put(numerus, numerusMap);
                    }
                });

        return NounDeclension.createOption(declensionMap);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Noun)) return false;
        if (!super.equals(o)) return false;
        Noun noun = (Noun) o;
        return genus == noun.genus &&
                Objects.equals(declensions, noun.declensions);
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), genus, declensions);
    }


    @Override
    public String toString() {
        return "Noun{" +
                "\n\tlemma info='" + super.toString() +
                ",\n\tgenus=" + genus +
                ",\n\tdeclension=" + getDeclension() +
                ",\n\talternativeDeclension=" + getAlternativeDeclension() +
                "\n}";
    }
}
