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

import java.util.*;
import java.util.stream.Collectors;

public class NounDeclension {
    private static final String NO_NUMERUS_MAP = "•";
    private static final String NO_KASUS_ITEM = "—";

    private static final String ITEM_SEPARATOR = ", ";


    public static Optional<NounDeclension> createOption(
            String singularNominative,
            String singularAccusative,
            String singularDative,
            String singularGenitive,

            String pluralNominative,
            String pluralAccusative,
            String pluralDative,
            String pluralGenitive
    ) {
        Map<Kasus, String> singularMap = new HashMap<>();
        singularMap.put(Kasus.NOMINATIV, singularNominative);
        singularMap.put(Kasus.AKKUSATIV, singularAccusative);
        singularMap.put(Kasus.DATIV, singularDative);
        singularMap.put(Kasus.GENITIV, singularGenitive);

        Map<Kasus, String> pluralMap = new HashMap<>();
        pluralMap.put(Kasus.NOMINATIV, pluralNominative);
        pluralMap.put(Kasus.AKKUSATIV, pluralAccusative);
        pluralMap.put(Kasus.DATIV, pluralDative);
        pluralMap.put(Kasus.GENITIV, pluralGenitive);

        Map<Numerus, Map<Kasus, String>> declensionMap = new HashMap<>();

        declensionMap.put(Numerus.SINGULAR, singularMap);
        declensionMap.put(Numerus.PLURAL, pluralMap);

        return createOption(declensionMap);
    }


    public static Optional<NounDeclension> createOption(Map<Numerus, Map<Kasus, String>> declensionMap) {
        Objects.requireNonNull(declensionMap);

        Map<Numerus, Map<Kasus, String>> filteredDeclensionMap =
                declensionMap
                        .entrySet()
                        .stream()
                        .map(declensionMapEntry -> {
                            Numerus numerus =
                                    declensionMapEntry.getKey();

                            Map<Kasus, String> numerusMap =
                                    declensionMapEntry.getValue();

                            Map<Kasus, String> filteredNumerusMap =
                                    numerusMap
                                            .entrySet()
                                            .stream()
                                            .filter(numerusMapEntry -> {
                                                String expression =
                                                        numerusMapEntry.getValue();

                                                return expression != null && !expression.isEmpty();
                                            })
                                            .collect(Collectors.toMap(
                                                    Map.Entry::getKey,
                                                    Map.Entry::getValue
                                            ));

                            return new AbstractMap.SimpleEntry<>(
                                    numerus,
                                    filteredNumerusMap
                            );

                        })
                        .filter(declensionMapEntry ->
                                !declensionMapEntry.getValue().isEmpty()
                        )
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        ));


        if (filteredDeclensionMap.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(
                    new NounDeclension(filteredDeclensionMap)
            );
        }
    }


    private static String formatDeclensionList(List<String> list) {
        String itemsString = String.join(
                ITEM_SEPARATOR,
                list
        );

        return String.format("[%s]", itemsString);
    }


    private final Map<Numerus, Map<Kasus, String>> declensionMap;


    private NounDeclension(Map<Numerus, Map<Kasus, String>> declensionMap) {
        this.declensionMap = declensionMap;
    }


    public Optional<String> getExpression(Numerus numerus, Kasus kasus) {
        return Optional.ofNullable(
                declensionMap
                        .getOrDefault(
                                numerus,
                                Collections.emptyMap()
                        )
                        .get(kasus)
        );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NounDeclension)) return false;
        NounDeclension that = (NounDeclension) o;
        return Objects.equals(declensionMap, that.declensionMap);
    }


    @Override
    public int hashCode() {
        return Objects.hash(declensionMap);
    }


    @Override
    public String toString() {
        List<String> numerusList =
                Arrays
                        .stream(Numerus.values())
                        .map(this::getNumerusListString)
                        .collect(Collectors.toList());

        return formatDeclensionList(numerusList);
    }


    private String getNumerusListString(Numerus numerus) {
        Map<Kasus, String> numerusMap =
                declensionMap.get(numerus);

        if (numerusMap == null) {
            return NO_NUMERUS_MAP;
        } else {
            List<String> kasusList =
                    Arrays
                            .stream(Kasus.values())
                            .map(kasus ->
                                    numerusMap.getOrDefault(kasus, NO_KASUS_ITEM)
                            )
                            .collect(Collectors.toList());

            return formatDeclensionList(kasusList);
        }
    }
}
