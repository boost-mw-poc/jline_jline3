/*
 * Copyright (c) 2002-2025, the original author(s).
 *
 * This software is distributable under the BSD license. See the terms of the
 * BSD license in the documentation provided with this software.
 *
 * https://opensource.org/licenses/BSD-3-Clause
 */
package org.jline.reader;

import java.util.Objects;

/**
 * Represents a completion candidate for tab completion.
 * <p>
 * A Candidate encapsulates all the information needed to display and apply a
 * completion suggestion. This includes the actual text to be inserted, how it
 * should be displayed to the user, grouping information, descriptions, and
 * other metadata that controls how the candidate behaves when selected.
 * <p>
 * Candidates are created by {@link Completer} implementations and passed to the
 * LineReader, which then filters, sorts, and displays them to the user when
 * tab completion is requested.
 * <p>
 * Each candidate has several properties:
 * <ul>
 *   <li>value - The actual text to be inserted when the candidate is selected</li>
 *   <li>display - How the candidate should be displayed to the user (may include ANSI styling)</li>
 *   <li>group - Optional grouping category for organizing related candidates</li>
 *   <li>description - Optional help text explaining the candidate</li>
 *   <li>suffix - Optional text to append when the candidate is selected</li>
 *   <li>complete - Whether the candidate is a complete word or may be further expanded</li>
 * </ul>
 *
 * @see Completer
 * @see LineReader.Option#AUTO_GROUP
 * @see LineReader.Option#GROUP
 */
public class Candidate implements Comparable<Candidate> {

    private final String value;
    private final String displ;
    private final String group;
    private final String descr;
    private final String suffix;
    private final String key;
    private final boolean complete;
    private final int sort;

    /**
     * Simple constructor with only a single String as an argument.
     *
     * @param value the candidate
     */
    public Candidate(String value) {
        this(value, value, null, null, null, null, true, 0);
    }

    /**
     * Constructs a new Candidate.
     *
     * @param value the value
     * @param displ the display string
     * @param group the group
     * @param descr the description
     * @param suffix the suffix
     * @param key the key
     * @param complete the complete flag
     * @param sort the sort flag
     */
    public Candidate(
            String value,
            String displ,
            String group,
            String descr,
            String suffix,
            String key,
            boolean complete,
            int sort) {
        this.value = Objects.requireNonNull(value);
        this.displ = Objects.requireNonNull(displ);
        this.group = group;
        this.descr = descr;
        this.suffix = suffix;
        this.key = key;
        this.complete = complete;
        this.sort = sort;
    }

    /**
     * Constructs a new Candidate.
     *
     * @param value the value
     * @param displ the display string
     * @param group the group
     * @param descr the description
     * @param suffix the suffix
     * @param key the key
     * @param complete the complete flag
     */
    public Candidate(
            String value, String displ, String group, String descr, String suffix, String key, boolean complete) {
        this(value, displ, group, descr, suffix, key, complete, 0);
    }

    /**
     * The value that will be used for the actual completion.
     * This string should not contain ANSI sequences.
     * @return the value
     */
    public String value() {
        return value;
    }

    /**
     * The string that will be displayed to the user.
     * This string may contain ANSI sequences.
     * @return the display string
     */
    public String displ() {
        return displ;
    }

    /**
     * The group name for this candidate.
     * Candidates can be grouped together and this string is used
     * as a key for the group and displayed to the user.
     * @return the group
     *
     * @see LineReader.Option#GROUP
     * @see LineReader.Option#AUTO_GROUP
     */
    public String group() {
        return group;
    }

    /**
     * Description of this candidate, usually a small help message
     * to understand the meaning of this candidate.
     * This string may contain ANSI sequences.
     * @return the description
     */
    public String descr() {
        return descr;
    }

    /**
     * The suffix is added when this candidate is displayed.
     * However, if the next character entered does not match,
     * the suffix will be automatically removed.
     * This string should not contain ANSI sequences.
     * @return the suffix
     *
     * @see LineReader.Option#AUTO_REMOVE_SLASH
     * @see LineReader#REMOVE_SUFFIX_CHARS
     */
    public String suffix() {
        return suffix;
    }

    /**
     * Candidates which have the same key will be merged together.
     * For example, if a command has multiple aliases, they can be merged
     * if they are using the same key.
     * @return the key
     */
    public String key() {
        return key;
    }

    /**
     * Boolean indicating whether this candidate is complete or
     * if the completer may further expand the candidate value
     * after this candidate has been selected.
     * This can be the case when completing folders for example.
     * If the candidate is complete and is selected, a space
     * separator will be added.
     * @return the completion flag
     */
    public boolean complete() {
        return complete;
    }

    /**
     * Integer used to override default sort logic.
     * @return the sort int
     */
    public int sort() {
        return sort;
    }

    @Override
    public int compareTo(Candidate o) {
        // If both candidates have same sort, use default behavior
        if (sort == o.sort()) {
            return value.compareTo(o.value);
        } else {
            return Integer.compare(sort, o.sort());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return Objects.equals(value, candidate.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "Candidate{" + value + "}";
    }
}
