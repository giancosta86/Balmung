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

import org.junit.Test;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class ConversionsTest {
    private final String testString = "This is a test";

    private final List<Character> testList = Arrays.asList('T', 'h', 'i', 's', ' ', 'i', 's', ' ', 'a', ' ', 't', 'e', 's', 't');


    @Test
    public void stringToStreamShouldWork() {
        List<Character> retrievedList =
                Conversions
                        .stringToStream(testString)
                        .collect(Collectors.toList());

        assertThat(
                retrievedList,
                equalTo(testList)
        );
    }


    @Test
    public void streamToStringShouldWork() {
        String retrievedString =
                Conversions
                        .streamToString(testList.stream());

        assertThat(
                retrievedString,
                equalTo(testString)
        );
    }


    @Test
    public void stringToDequeShouldWork() {
        Deque<Character> retrievedDeque =
                Conversions.stringToDeque(testString);

        Deque<Character> expectedDeque =
                new LinkedList<>(testList);

        assertThat(
                retrievedDeque,
                equalTo(expectedDeque)
        );
    }


    @Test
    public void stringToListShouldWork() {
        List<Character> retrievedList =
                Conversions.stringToList(testString);

        assertThat(
                retrievedList,
                equalTo(testList)
        );
    }
}
