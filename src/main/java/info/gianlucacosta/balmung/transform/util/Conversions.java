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

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Conversions {
    static Stream<Character> stringToStream(String sourceString) {
        return sourceString
                .chars()
                .mapToObj(charCode -> (char) charCode);
    }


    static String streamToString(Stream<Character> sourceStream) {
        StringBuilder result = new StringBuilder();

        sourceStream.forEach(result::append);

        return result.toString();
    }


    static Deque<Character> stringToDeque(String source) {
        return
                stringToStream(source)
                        .collect(Collectors.toCollection(LinkedList::new));
    }


    static List<Character> stringToList(String source) {
        return
                stringToStream(source)
                        .collect(Collectors.toList());
    }
}
