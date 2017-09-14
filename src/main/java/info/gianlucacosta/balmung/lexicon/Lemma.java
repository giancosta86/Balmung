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
import java.util.stream.Collectors;

@Entity
@Table(name = "Lemmas")
@IdClass(LemmaId.class)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Lemma {
    private static final String CATEGORIES_SEPARATOR = ",";

    @Id
    private String expression;

    @Id
    @Column(name = "categories")
    private String categoriesString;

    private transient Set<String> categories;

    @ElementCollection
    @OrderColumn(name = "index")
    @Column(
            name = "syllable",
            nullable = false
    )
    @CollectionTable(
            name = "Syllables",
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
                    columnNames = {"lemmaExpression", "lemmaCategories", "index", "syllable"}
            )
    )
    private List<String> syllables;

    @Basic(fetch = FetchType.LAZY)
    private String pronunciation;

    @ElementCollection
    @Column(
            name = "synonym",
            nullable = false
    )
    @CollectionTable(
            name = "Synonyms",
            joinColumns = {
                    @JoinColumn(
                            name = "lemmaExpression",
                            referencedColumnName = "expression"
                    ),

                    @JoinColumn(
                            name = "lemmaCategories",
                            referencedColumnName = "categories"
                    )
            }
    )
    private Set<String> synonyms;


    @ElementCollection
    @Column(
            name = "antonym",
            nullable = false
    )
    @CollectionTable(
            name = "Antonyms",
            joinColumns = {
                    @JoinColumn(
                            name = "lemmaExpression",
                            referencedColumnName = "expression"
                    ),

                    @JoinColumn(
                            name = "lemmaCategories",
                            referencedColumnName = "categories"
                    )
            }
    )
    private Set<String> antonyms;


    @ElementCollection
    @Column(
            name = "hypernym",
            nullable = false
    )
    @CollectionTable(
            name = "Hypernyms",
            joinColumns = {
                    @JoinColumn(
                            name = "lemmaExpression",
                            referencedColumnName = "expression"
                    ),

                    @JoinColumn(
                            name = "lemmaCategories",
                            referencedColumnName = "categories"
                    )
            }
    )
    private Set<String> hypernyms;


    Lemma() {
    }


    public Lemma(
            String expression,
            Set<String> categories
    ) {
        this(
                expression,
                categories,
                Collections.emptyList(),
                Optional.empty(),
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet()
        );
    }


    public Lemma(
            String expression,
            Set<String> categories,
            List<String> syllables,
            Optional<String> pronunciationOption,
            Set<String> synonyms,
            Set<String> antonyms,
            Set<String> hypernyms
    ) {
        Objects.requireNonNull(expression);
        Objects.requireNonNull(categories);
        Objects.requireNonNull(syllables);
        Objects.requireNonNull(pronunciationOption);
        Objects.requireNonNull(synonyms);
        Objects.requireNonNull(antonyms);
        Objects.requireNonNull(hypernyms);

        if (categories.isEmpty()) {
            throw new IllegalArgumentException("At least one category must be provided");
        }

        this.expression = expression;
        this.categoriesString = String.join(
                CATEGORIES_SEPARATOR,
                categories.stream().sorted().collect(Collectors.toList())
        );

        this.syllables = Collections.unmodifiableList(syllables);
        this.pronunciation = pronunciationOption.orElse(null);

        this.synonyms = Collections.unmodifiableSet(synonyms);
        this.antonyms = Collections.unmodifiableSet(antonyms);
        this.hypernyms = Collections.unmodifiableSet(hypernyms);
    }


    public String getExpression() {
        return expression;
    }


    public Set<String> getCategories() {
        if (categories == null) {
            categories =
                    new HashSet<>(
                            Arrays.asList(
                                    categoriesString.split(CATEGORIES_SEPARATOR)
                            )
                    );
        }

        return categories;
    }

    public List<String> getSyllables() {
        return syllables;
    }

    public Optional<String> getPronunciation() {
        return Optional.ofNullable(pronunciation);
    }

    public Set<String> getSynonyms() {
        return synonyms;
    }

    public Set<String> getAntonyms() {
        return antonyms;
    }

    public Set<String> getHypernyms() {
        return hypernyms;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lemma)) return false;
        Lemma lemma = (Lemma) o;
        return Objects.equals(expression, lemma.expression) &&
                Objects.equals(categoriesString, lemma.categoriesString) &&
                Objects.equals(syllables, lemma.syllables) &&
                Objects.equals(pronunciation, lemma.pronunciation) &&
                Objects.equals(synonyms, lemma.synonyms) &&
                Objects.equals(antonyms, lemma.antonyms) &&
                Objects.equals(hypernyms, lemma.hypernyms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, categoriesString);
    }

    @Override
    public String toString() {
        return "Lemma{" +
                "\n\texpression='" + expression + '\'' +
                ",\n\tcategories=" + getCategories() +
                ",\n\tsyllables=" + syllables +
                ",\n\tpronunciation='" + pronunciation + '\'' +
                ",\n\tsynonyms=" + synonyms +
                ",\n\tantonyms=" + antonyms +
                ",\n\thypernyms=" + hypernyms +
                "\n}";
    }
}
