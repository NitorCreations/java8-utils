package com.nitorcreations.streams;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class NStreamsUtilsTest {

  @Test
  public void test_concatenation_empty_lists() {
    List<String> emptylist = new ArrayList<>();
    List<String> combinedList = NStreamsUtils.concat(emptylist.stream(), emptylist.stream())
        .collect(toList());
    assertThat(combinedList).isEmpty();
  }

  @Test
  public void test_concatenation() {
    List<String> list1 = asList("1", "11");
    List<String> list2 = asList("2", "22");
    List<String> list3 = asList("3");

    List<String> combinedList = NStreamsUtils.concat(list1.stream(), list2.stream(), list3.stream()).collect(toList());
    assertThat(combinedList).containsExactly("1","11","2","22","3");
  }
}
