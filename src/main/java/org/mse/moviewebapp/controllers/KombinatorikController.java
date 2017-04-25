package org.mse.moviewebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Ferdinand.Szekeresch on 25.04.2017.
 */
@Controller
public class KombinatorikController {

	@RequestMapping("/foo")
	public @ResponseBody String foo() {
		Deque<String> a = new ArrayDeque<>();
		a.add("a1");
		a.add("a2");
		a.add("a3");
		Deque<String> b = new ArrayDeque<>();
		b.add("b1");
		Deque<String> c = new ArrayDeque<>();
		c.add("c1");
		c.add("c2");
		c.add("c3");
		Deque<String> d = new ArrayDeque<>();
		d.add("d1");
		d.add("d2");

		Deque<Deque<String>> deque = new ArrayDeque<Deque<String>>();
		deque.add(a);
		deque.add(b);
		deque.add(c);
		deque.add(d);

		final List<Set<String>> sets = generatePermutations(deque);

		return sets.stream().map(this::permutationToString).collect(Collectors.joining("\n"));
	}

	private String permutationToString(Set<String> strings) {
		return strings.stream().collect(Collectors.joining(","));
	}

	private List<Set<String>> generatePermutations(Deque<Deque<String>> lists) {
		final Deque<String> firstDeque = lists.pop();
		if (lists.isEmpty()) {
			return firstDeque.stream().map(this::stringToSet).collect(Collectors.toList());
		}
		List<Set<String>> permutations = generatePermutations(lists);
		return firstDeque.stream().flatMap(s -> addStringToAllPermutations(s, permutations)).collect(Collectors.toList());
	}

	private Stream<? extends Set<String>> addStringToAllPermutations(String s, List<Set<String>> permutations) {
		return permutations.stream().map(HashSet::new).peek(set -> set.add(s));
	}

	private Set<String> stringToSet(String s) {
		HashSet<String> set = new HashSet<>();
		set.add(s);
		return set;
	}



}
