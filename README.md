# Music Library
[![Build Status][travis-image]][travis-url]
[![Maven Central][maven-image]][maven-url]
[![Coverage Status][coveralls-image]][coveralls-url]

[![License][license-image]][license-url]

Java library for music downloads

### download the track

    MusicLibrary musicLibrary = MusicLibrary.createDefaultLibrary(host);
    musicLibrary.searchTrack("Kasabian", "Underdog")
                .ifPresent(track -> {
                    try(InputStream in = musicLibrary.fetchInputStream(track)) {
                        Files.copy(in, Paths.get("Kasabian - Underdog.mp3"))
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                
### guess the track

    MusicGuesser lib = MusicGuesser.createDefaultGuesser(host);
    List<String> searchResult = lib.suggest("Robert Jo");
    assert searchResult.contains("Robert johnson - little queen of spades");

[travis-image]: https://travis-ci.org/DNAlchemist/music-library.svg?branch=master
[travis-url]: https://travis-ci.org/DNAlchemist/music-library
[maven-image]: https://maven-badges.herokuapp.com/maven-central/one.chest.ratpack/music-library/badge.svg
[maven-url]: https://maven-badges.herokuapp.com/maven-central/one.chest.ratpack/music-library
[coveralls-image]: https://coveralls.io/repos/github/DNAlchemist/music-library/badge.svg?branch=master
[coveralls-url]: https://coveralls.io/github/DNAlchemist/music-library?branch=master

[license-url]: https://github.com/Mashape/unirest-java/blob/master/LICENSE
[license-image]: https://img.shields.io/badge/license-MIT-blue.svg?style=flat
