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
@Table(name = "Verbs")
public class Verb extends Lemma {
    private String praesens;
    private String praeteritum;
    private String partizipPerfekt;
    private String imperatifSingular;


    Verb() {
    }


    public Verb(
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
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
    }


    public Verb(
            String expression,
            Set<String> categories,
            List<String> syllables,
            Optional<String> pronunciationOption,
            Set<String> synonyms,
            Set<String> antonyms,
            Set<String> hypernyms,
            Optional<String> praesensOption,
            Optional<String> praeteritumOption,
            Optional<String> partizipPerfektOption,
            Optional<String> imperatifSingularOption
    ) {
        super(expression, categories, syllables, pronunciationOption, synonyms, antonyms, hypernyms);

        Objects.requireNonNull(praesensOption);
        Objects.requireNonNull(praeteritumOption);
        Objects.requireNonNull(partizipPerfektOption);
        Objects.requireNonNull(imperatifSingularOption);

        this.praesens = praesensOption.orElse(null);
        this.praeteritum = praeteritumOption.orElse(null);
        this.partizipPerfekt = partizipPerfektOption.orElse(null);
        this.imperatifSingular = imperatifSingularOption.orElse(null);
    }

    public Optional<String> getPraesens() {
        return Optional.ofNullable(praesens);
    }

    public Optional<String> getPraeteritum() {
        return Optional.ofNullable(praeteritum);
    }

    public Optional<String> getPartizipPerfekt() {
        return Optional.ofNullable(partizipPerfekt);
    }

    public Optional<String> getImperatifSingular() {
        return Optional.ofNullable(imperatifSingular);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Verb)) return false;
        if (!super.equals(o)) return false;
        Verb verb = (Verb) o;
        return Objects.equals(praesens, verb.praesens) &&
                Objects.equals(praeteritum, verb.praeteritum) &&
                Objects.equals(partizipPerfekt, verb.partizipPerfekt) &&
                Objects.equals(imperatifSingular, verb.imperatifSingular);
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), praesens, praeteritum, partizipPerfekt, imperatifSingular);
    }

    @Override
    public String toString() {
        return "Verb{" +
                "\n\tlemma info='" + super.toString() +
                ",\n\tpraesens='" + praesens + '\'' +
                ",\n\tpraeteritum='" + praeteritum + '\'' +
                ",\n\tpartizipPerfekt='" + partizipPerfekt + '\'' +
                ",\n\timperatifSingular='" + imperatifSingular + '\'' +
                "\n}";
    }
}
