use io

main(args: int[][]) {
    // 1 + 2 + 8 + 64 = 75
    print_bool_as_int({ true, false, true, true, false, true, false, false })

    // 4 + 8 + 64 + 128 = 204
    print_bool_as_int({ false, false, true, true, false, false, true, true })

    // 0
    print_bool_as_int({ true, true, true, true, true, true, true, true })

    // 1
    print_bool_as_int({ true, true, true, true, true, true, true, false })

    // 255
    print_bool_as_int({ false, false, false, false, false, false, false, false })
}

print_bool_as_int(branches: bool[]) {
if branches[0] {
    if branches[1] {
        if branches[2] {
            if branches[3] {
                if branches[4] {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("0")
                            } else {
                                println("1")
                            }
                        } else {
                            if branches[7] {
                                println("2")
                            } else {
                                println("3")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("4")
                            } else {
                                println("5")
                            }
                        } else {
                            if branches[7] {
                                println("6")
                            } else {
                                println("7")
                            }
                        }
                    }
                } else {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("8")
                            } else {
                                println("9")
                            }
                        } else {
                            if branches[7] {
                                println("10")
                            } else {
                                println("11")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("12")
                            } else {
                                println("13")
                            }
                        } else {
                            if branches[7] {
                                println("14")
                            } else {
                                println("15")
                            }
                        }
                    }
                }
            } else {
                if branches[4] {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("16")
                            } else {
                                println("17")
                            }
                        } else {
                            if branches[7] {
                                println("18")
                            } else {
                                println("19")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("20")
                            } else {
                                println("21")
                            }
                        } else {
                            if branches[7] {
                                println("22")
                            } else {
                                println("23")
                            }
                        }
                    }
                } else {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("24")
                            } else {
                                println("25")
                            }
                        } else {
                            if branches[7] {
                                println("26")
                            } else {
                                println("27")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("28")
                            } else {
                                println("29")
                            }
                        } else {
                            if branches[7] {
                                println("30")
                            } else {
                                println("31")
                            }
                        }
                    }
                }
            }
        } else {
            if branches[3] {
                if branches[4] {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("32")
                            } else {
                                println("33")
                            }
                        } else {
                            if branches[7] {
                                println("34")
                            } else {
                                println("35")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("36")
                            } else {
                                println("37")
                            }
                        } else {
                            if branches[7] {
                                println("38")
                            } else {
                                println("39")
                            }
                        }
                    }
                } else {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("40")
                            } else {
                                println("41")
                            }
                        } else {
                            if branches[7] {
                                println("42")
                            } else {
                                println("43")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("44")
                            } else {
                                println("45")
                            }
                        } else {
                            if branches[7] {
                                println("46")
                            } else {
                                println("47")
                            }
                        }
                    }
                }
            } else {
                if branches[4] {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("48")
                            } else {
                                println("49")
                            }
                        } else {
                            if branches[7] {
                                println("50")
                            } else {
                                println("51")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("52")
                            } else {
                                println("53")
                            }
                        } else {
                            if branches[7] {
                                println("54")
                            } else {
                                println("55")
                            }
                        }
                    }
                } else {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("56")
                            } else {
                                println("57")
                            }
                        } else {
                            if branches[7] {
                                println("58")
                            } else {
                                println("59")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("60")
                            } else {
                                println("61")
                            }
                        } else {
                            if branches[7] {
                                println("62")
                            } else {
                                println("63")
                            }
                        }
                    }
                }
            }
        }
    } else {
        if branches[2] {
            if branches[3] {
                if branches[4] {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("64")
                            } else {
                                println("65")
                            }
                        } else {
                            if branches[7] {
                                println("66")
                            } else {
                                println("67")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("68")
                            } else {
                                println("69")
                            }
                        } else {
                            if branches[7] {
                                println("70")
                            } else {
                                println("71")
                            }
                        }
                    }
                } else {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("72")
                            } else {
                                println("73")
                            }
                        } else {
                            if branches[7] {
                                println("74")
                            } else {
                                println("75")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("76")
                            } else {
                                println("77")
                            }
                        } else {
                            if branches[7] {
                                println("78")
                            } else {
                                println("79")
                            }
                        }
                    }
                }
            } else {
                if branches[4] {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("80")
                            } else {
                                println("81")
                            }
                        } else {
                            if branches[7] {
                                println("82")
                            } else {
                                println("83")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("84")
                            } else {
                                println("85")
                            }
                        } else {
                            if branches[7] {
                                println("86")
                            } else {
                                println("87")
                            }
                        }
                    }
                } else {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("88")
                            } else {
                                println("89")
                            }
                        } else {
                            if branches[7] {
                                println("90")
                            } else {
                                println("91")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("92")
                            } else {
                                println("93")
                            }
                        } else {
                            if branches[7] {
                                println("94")
                            } else {
                                println("95")
                            }
                        }
                    }
                }
            }
        } else {
            if branches[3] {
                if branches[4] {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("96")
                            } else {
                                println("97")
                            }
                        } else {
                            if branches[7] {
                                println("98")
                            } else {
                                println("99")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("100")
                            } else {
                                println("101")
                            }
                        } else {
                            if branches[7] {
                                println("102")
                            } else {
                                println("103")
                            }
                        }
                    }
                } else {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("104")
                            } else {
                                println("105")
                            }
                        } else {
                            if branches[7] {
                                println("106")
                            } else {
                                println("107")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("108")
                            } else {
                                println("109")
                            }
                        } else {
                            if branches[7] {
                                println("110")
                            } else {
                                println("111")
                            }
                        }
                    }
                }
            } else {
                if branches[4] {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("112")
                            } else {
                                println("113")
                            }
                        } else {
                            if branches[7] {
                                println("114")
                            } else {
                                println("115")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("116")
                            } else {
                                println("117")
                            }
                        } else {
                            if branches[7] {
                                println("118")
                            } else {
                                println("119")
                            }
                        }
                    }
                } else {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("120")
                            } else {
                                println("121")
                            }
                        } else {
                            if branches[7] {
                                println("122")
                            } else {
                                println("123")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("124")
                            } else {
                                println("125")
                            }
                        } else {
                            if branches[7] {
                                println("126")
                            } else {
                                println("127")
                            }
                        }
                    }
                }
            }
        }
    }
} else {
    if branches[1] {
        if branches[2] {
            if branches[3] {
                if branches[4] {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("128")
                            } else {
                                println("129")
                            }
                        } else {
                            if branches[7] {
                                println("130")
                            } else {
                                println("131")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("132")
                            } else {
                                println("133")
                            }
                        } else {
                            if branches[7] {
                                println("134")
                            } else {
                                println("135")
                            }
                        }
                    }
                } else {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("136")
                            } else {
                                println("137")
                            }
                        } else {
                            if branches[7] {
                                println("138")
                            } else {
                                println("139")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("140")
                            } else {
                                println("141")
                            }
                        } else {
                            if branches[7] {
                                println("142")
                            } else {
                                println("143")
                            }
                        }
                    }
                }
            } else {
                if branches[4] {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("144")
                            } else {
                                println("145")
                            }
                        } else {
                            if branches[7] {
                                println("146")
                            } else {
                                println("147")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("148")
                            } else {
                                println("149")
                            }
                        } else {
                            if branches[7] {
                                println("150")
                            } else {
                                println("151")
                            }
                        }
                    }
                } else {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("152")
                            } else {
                                println("153")
                            }
                        } else {
                            if branches[7] {
                                println("154")
                            } else {
                                println("155")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("156")
                            } else {
                                println("157")
                            }
                        } else {
                            if branches[7] {
                                println("158")
                            } else {
                                println("159")
                            }
                        }
                    }
                }
            }
        } else {
            if branches[3] {
                if branches[4] {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("160")
                            } else {
                                println("161")
                            }
                        } else {
                            if branches[7] {
                                println("162")
                            } else {
                                println("163")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("164")
                            } else {
                                println("165")
                            }
                        } else {
                            if branches[7] {
                                println("166")
                            } else {
                                println("167")
                            }
                        }
                    }
                } else {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("168")
                            } else {
                                println("169")
                            }
                        } else {
                            if branches[7] {
                                println("170")
                            } else {
                                println("171")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("172")
                            } else {
                                println("173")
                            }
                        } else {
                            if branches[7] {
                                println("174")
                            } else {
                                println("175")
                            }
                        }
                    }
                }
            } else {
                if branches[4] {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("176")
                            } else {
                                println("177")
                            }
                        } else {
                            if branches[7] {
                                println("178")
                            } else {
                                println("179")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("180")
                            } else {
                                println("181")
                            }
                        } else {
                            if branches[7] {
                                println("182")
                            } else {
                                println("183")
                            }
                        }
                    }
                } else {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("184")
                            } else {
                                println("185")
                            }
                        } else {
                            if branches[7] {
                                println("186")
                            } else {
                                println("187")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("188")
                            } else {
                                println("189")
                            }
                        } else {
                            if branches[7] {
                                println("190")
                            } else {
                                println("191")
                            }
                        }
                    }
                }
            }
        }
    } else {
        if branches[2] {
            if branches[3] {
                if branches[4] {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("192")
                            } else {
                                println("193")
                            }
                        } else {
                            if branches[7] {
                                println("194")
                            } else {
                                println("195")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("196")
                            } else {
                                println("197")
                            }
                        } else {
                            if branches[7] {
                                println("198")
                            } else {
                                println("199")
                            }
                        }
                    }
                } else {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("200")
                            } else {
                                println("201")
                            }
                        } else {
                            if branches[7] {
                                println("202")
                            } else {
                                println("203")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("204")
                            } else {
                                println("205")
                            }
                        } else {
                            if branches[7] {
                                println("206")
                            } else {
                                println("207")
                            }
                        }
                    }
                }
            } else {
                if branches[4] {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("208")
                            } else {
                                println("209")
                            }
                        } else {
                            if branches[7] {
                                println("210")
                            } else {
                                println("211")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("212")
                            } else {
                                println("213")
                            }
                        } else {
                            if branches[7] {
                                println("214")
                            } else {
                                println("215")
                            }
                        }
                    }
                } else {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("216")
                            } else {
                                println("217")
                            }
                        } else {
                            if branches[7] {
                                println("218")
                            } else {
                                println("219")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("220")
                            } else {
                                println("221")
                            }
                        } else {
                            if branches[7] {
                                println("222")
                            } else {
                                println("223")
                            }
                        }
                    }
                }
            }
        } else {
            if branches[3] {
                if branches[4] {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("224")
                            } else {
                                println("225")
                            }
                        } else {
                            if branches[7] {
                                println("226")
                            } else {
                                println("227")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("228")
                            } else {
                                println("229")
                            }
                        } else {
                            if branches[7] {
                                println("230")
                            } else {
                                println("231")
                            }
                        }
                    }
                } else {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("232")
                            } else {
                                println("233")
                            }
                        } else {
                            if branches[7] {
                                println("234")
                            } else {
                                println("235")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("236")
                            } else {
                                println("237")
                            }
                        } else {
                            if branches[7] {
                                println("238")
                            } else {
                                println("239")
                            }
                        }
                    }
                }
            } else {
                if branches[4] {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("240")
                            } else {
                                println("241")
                            }
                        } else {
                            if branches[7] {
                                println("242")
                            } else {
                                println("243")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("244")
                            } else {
                                println("245")
                            }
                        } else {
                            if branches[7] {
                                println("246")
                            } else {
                                println("247")
                            }
                        }
                    }
                } else {
                    if branches[5] {
                        if branches[6] {
                            if branches[7] {
                                println("248")
                            } else {
                                println("249")
                            }
                        } else {
                            if branches[7] {
                                println("250")
                            } else {
                                println("251")
                            }
                        }
                    } else {
                        if branches[6] {
                            if branches[7] {
                                println("252")
                            } else {
                                println("253")
                            }
                        } else {
                            if branches[7] {
                                println("254")
                            } else {
                                println("255")
                            }
                        }
                    }
                }
            }
        }
    }
}
}
