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

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Vowels {
    Set<Character> PLAIN_VOWELS =
            Arrays
                    .stream(
                            new Character[]{'A', 'E', 'I', 'O', 'U', 'a', 'e', 'i', 'o', 'u'}
                    )
                    .collect(Collectors.toSet());


    Set<Character> UMLAUT_VOWELS =
            Arrays
                    .stream(
                            new Character[]{'Ä', 'Ö', 'Ü', 'ä', 'ö', 'ü'}
                    )
                    .collect(Collectors.toSet());


    Set<Character> VOWELS =
            Stream.concat(
                    PLAIN_VOWELS.stream(),
                    UMLAUT_VOWELS.stream()
            )
                    .collect(Collectors.toSet());


    static boolean isPlainVowel(char c) {
        return PLAIN_VOWELS.contains(c);
    }


    static boolean isUmlautVowel(char c) {
        return UMLAUT_VOWELS.contains(c);
    }


    static boolean isVowel(char c) {
        return VOWELS.contains(c);
    }


    static char addUmlaut(char c) {
        switch (c) {
            case 'A':
                return 'Ä';

            case 'a':
                return 'ä';

            case 'E':
            case 'I':
            case 'Ä':
            case 'Ö':
            case 'Ü':
            case 'e':
            case 'i':
            case 'ä':
            case 'ö':
            case 'ü':
                return c;

            case 'O':
                return 'Ö';

            case 'o':
                return 'ö';

            case 'U':
                return 'Ü';

            case 'u':
                return 'ü';

            default:
                throw new IllegalArgumentException(String.format("'%c' is not a vowel", c));
        }
    }


    static char removeUmlaut(char c) {
        switch (c) {
            case 'Ä':
                return 'A';

            case 'ä':
                return 'a';

            case 'A':
            case 'E':
            case 'I':
            case 'O':
            case 'U':
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return c;

            case 'Ö':
                return 'O';

            case 'ö':
                return 'o';

            case 'Ü':
                return 'U';

            case 'ü':
                return 'u';

            default:
                throw new IllegalArgumentException(String.format("'%c' is not a vowel", c));
        }
    }


    static boolean isUmlautAdded(char source, char target) {
        switch (Character.toLowerCase(source)) {
            case 'a':
                return Character.toLowerCase(target) == 'ä';
            case 'o':
                return Character.toLowerCase(target) == 'ö';
            case 'u':
                return Character.toLowerCase(target) == 'ü';

            default:
                return false;
        }
    }


    static boolean canReceiveUmlaut(char c) {
        switch (c) {
            case 'A':
            case 'a':
            case 'O':
            case 'o':
            case 'U':
            case 'u':
                return true;

            default:
                return false;
        }
    }
}
