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

import info.gianlucacosta.balmung.transform.Transform;
import info.gianlucacosta.balmung.transform.util.Conversions;
import info.gianlucacosta.balmung.transform.util.Vowels;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

public class SuffixTransform implements Transform {
    private final String suffixToRemove;
    private final boolean addUmlaut;
    private final String suffixToAdd;


    public static SuffixTransform compute(String origin, String result) {
        if (origin.length() > result.length()) {
            throw new IllegalArgumentException(
                    String.format(
                            "This transform can only produce a result whose length is >= than the initial string's. However, '%s' is shorter than the original '%s'",
                            result,
                            origin
                    ));
        }

        Deque<Character> originDeque =
                Conversions.stringToDeque(origin);

        Deque<Character> resultDeque =
                Conversions.stringToDeque(result);

        boolean wasUmlautAdded = false;

        while (!originDeque.isEmpty()) {
            char originChar =
                    originDeque.peekFirst();

            char resultChar =
                    resultDeque.peekFirst();

            if (originChar != resultChar) {
                if (!wasUmlautAdded && Vowels.isUmlautAdded(originChar, resultChar)) {
                    wasUmlautAdded = true;
                } else {
                    break;
                }
            }

            originDeque.removeFirst();
            resultDeque.removeFirst();
        }

        String removedSuffix =
                Conversions.streamToString(originDeque.stream());

        String addedSuffix =
                Conversions.streamToString(resultDeque.stream());

        return new SuffixTransform(removedSuffix, wasUmlautAdded, addedSuffix);
    }


    public SuffixTransform(String suffixToRemove, boolean addUmlaut, String suffixToAdd) {
        Objects.requireNonNull(suffixToRemove);
        Objects.requireNonNull(suffixToAdd);

        this.suffixToRemove = suffixToRemove;
        this.addUmlaut = addUmlaut;
        this.suffixToAdd = suffixToAdd;
    }

    public String getSuffixToRemove() {
        return suffixToRemove;
    }

    public boolean isAddUmlaut() {
        return addUmlaut;
    }

    public String getSuffixToAdd() {
        return suffixToAdd;
    }


    @Override
    public String apply(String origin) {
        if (origin.length() < suffixToRemove.length()) {
            throw new IllegalArgumentException(
                    String.format(
                            "The original string ('%s') must not be shorter than the suffix to remove ('%s')!",
                            origin,
                            suffixToRemove
                    )
            );
        }

        Deque<Character> originDeque =
                Conversions.stringToDeque(origin);

        Deque<Character> suffixToRemoveDeque =
                Conversions.stringToDeque(suffixToRemove);

        while (!suffixToRemoveDeque.isEmpty()) {
            char originChar =
                    originDeque.removeLast();

            char charToRemove =
                    suffixToRemoveDeque.removeLast();

            if (originChar != charToRemove) {
                throw new IllegalArgumentException(
                        String.format("'%s' does not end with '%s'",
                                origin,
                                suffixToRemove
                        )
                );
            }
        }


        Deque<Character> resultDeque = new LinkedList<>(
                Conversions.stringToList(suffixToAdd)
        );


        boolean umlautApplied = false;

        while (!originDeque.isEmpty()) {
            char finalOriginCharacter =
                    originDeque.removeLast();

            if (addUmlaut && !umlautApplied) {
                if (Vowels.canReceiveUmlaut(finalOriginCharacter)) {
                    resultDeque.addFirst(
                            Vowels.addUmlaut(finalOriginCharacter)
                    );

                    umlautApplied = true;
                } else {
                    resultDeque.addFirst(finalOriginCharacter);
                }
            } else {
                resultDeque.addFirst(finalOriginCharacter);
            }
        }

        return Conversions.streamToString(resultDeque.stream());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SuffixTransform)) return false;
        SuffixTransform that = (SuffixTransform) o;
        return addUmlaut == that.addUmlaut &&
                Objects.equals(suffixToRemove, that.suffixToRemove) &&
                Objects.equals(suffixToAdd, that.suffixToAdd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suffixToRemove, addUmlaut, suffixToAdd);
    }

    @Override
    public String toString() {
        String addedSuffixHyphen =
                addUmlaut ?
                        "⸚"
                        :
                        "-";

        if (suffixToAdd.isEmpty()) {
            return addedSuffixHyphen;
        } else {
            if (suffixToRemove.isEmpty()) {
                return addedSuffixHyphen + suffixToAdd;
            } else {
                return String.format(
                        "-%s ⇒ %s%s",
                        suffixToRemove,
                        addedSuffixHyphen,
                        suffixToAdd
                );
            }
        }
    }
}
