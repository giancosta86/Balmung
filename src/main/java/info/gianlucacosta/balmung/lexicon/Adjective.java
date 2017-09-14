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

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;


@Entity
@Table(name = "Adjectives")
public class Adjective extends Lemma {
    private String comparative;
    private String superlative;


    Adjective() {
    }


    public Adjective(
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
                Collections.emptySet(),
                Optional.empty(),
                Optional.empty()
        );
    }


    public Adjective(
            String expression,
            Set<String> categories,
            List<String> syllables,
            Optional<String> pronunciationOption,
            Set<String> synonyms,
            Set<String> antonyms,
            Set<String> hypernyms,
            Optional<String> comparativeOption,
            Optional<String> superlativeOption
    ) {
        super(expression, categories, syllables, pronunciationOption, synonyms, antonyms, hypernyms);

        Objects.requireNonNull(comparativeOption);
        Objects.requireNonNull(superlativeOption);

        this.comparative = comparativeOption.orElse(null);
        this.superlative = superlativeOption.orElse(null);
    }

    public Optional<String> getComparative() {
        return Optional.ofNullable(comparative);
    }

    public Optional<String> getSuperlative() {
        return Optional.ofNullable(superlative);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Adjective)) return false;
        if (!super.equals(o)) return false;
        Adjective adjective = (Adjective) o;
        return Objects.equals(comparative, adjective.comparative) &&
                Objects.equals(superlative, adjective.superlative);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), comparative, superlative);
    }

    @Override
    public String toString() {
        return "Adjective{" +
                "\n\tlemma info='" + super.toString() +
                ",\n\tcomparative='" + comparative + '\'' +
                ",\n\tsuperlative='" + superlative + '\'' +
                "\n}";
    }
}
