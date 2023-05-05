function cov_z33e6goor() {
  var path = "C:\\Users\\Tomas\\systemutvikling\\idatt2106_2023_6\\frontend\\src\\services\\StatsService.js";
  var hash = "4a5222b7c2cd06507f0e030663190b55ad977ebc";
  var global = new Function("return this")();
  var gcv = "__coverage__";
  var coverageData = {
    path: "C:\\Users\\Tomas\\systemutvikling\\idatt2106_2023_6\\frontend\\src\\services\\StatsService.js",
    statementMap: {
      "0": {
        start: {
          line: 4,
          column: 25
        },
        end: {
          line: 4,
          column: 57
        }
      },
      "1": {
        start: {
          line: 5,
          column: 31
        },
        end: {
          line: 11,
          column: 1
        }
      },
      "2": {
        start: {
          line: 6,
          column: 4
        },
        end: {
          line: 10,
          column: 7
        }
      },
      "3": {
        start: {
          line: 13,
          column: 28
        },
        end: {
          line: 19,
          column: 1
        }
      },
      "4": {
        start: {
          line: 14,
          column: 4
        },
        end: {
          line: 18,
          column: 7
        }
      },
      "5": {
        start: {
          line: 21,
          column: 38
        },
        end: {
          line: 27,
          column: 1
        }
      },
      "6": {
        start: {
          line: 22,
          column: 4
        },
        end: {
          line: 26,
          column: 7
        }
      },
      "7": {
        start: {
          line: 29,
          column: 33
        },
        end: {
          line: 35,
          column: 1
        }
      },
      "8": {
        start: {
          line: 30,
          column: 4
        },
        end: {
          line: 34,
          column: 7
        }
      },
      "9": {
        start: {
          line: 37,
          column: 40
        },
        end: {
          line: 43,
          column: 1
        }
      },
      "10": {
        start: {
          line: 38,
          column: 4
        },
        end: {
          line: 42,
          column: 7
        }
      },
      "11": {
        start: {
          line: 45,
          column: 35
        },
        end: {
          line: 51,
          column: 1
        }
      },
      "12": {
        start: {
          line: 46,
          column: 4
        },
        end: {
          line: 50,
          column: 7
        }
      }
    },
    fnMap: {
      "0": {
        name: "(anonymous_0)",
        decl: {
          start: {
            line: 5,
            column: 31
          },
          end: {
            line: 5,
            column: 32
          }
        },
        loc: {
          start: {
            line: 5,
            column: 66
          },
          end: {
            line: 11,
            column: 1
          }
        },
        line: 5
      },
      "1": {
        name: "(anonymous_1)",
        decl: {
          start: {
            line: 13,
            column: 28
          },
          end: {
            line: 13,
            column: 29
          }
        },
        loc: {
          start: {
            line: 13,
            column: 62
          },
          end: {
            line: 19,
            column: 1
          }
        },
        line: 13
      },
      "2": {
        name: "(anonymous_2)",
        decl: {
          start: {
            line: 21,
            column: 38
          },
          end: {
            line: 21,
            column: 39
          }
        },
        loc: {
          start: {
            line: 21,
            column: 50
          },
          end: {
            line: 27,
            column: 1
          }
        },
        line: 21
      },
      "3": {
        name: "(anonymous_3)",
        decl: {
          start: {
            line: 29,
            column: 33
          },
          end: {
            line: 29,
            column: 34
          }
        },
        loc: {
          start: {
            line: 29,
            column: 45
          },
          end: {
            line: 35,
            column: 1
          }
        },
        line: 29
      },
      "4": {
        name: "(anonymous_4)",
        decl: {
          start: {
            line: 37,
            column: 40
          },
          end: {
            line: 37,
            column: 41
          }
        },
        loc: {
          start: {
            line: 37,
            column: 60
          },
          end: {
            line: 43,
            column: 1
          }
        },
        line: 37
      },
      "5": {
        name: "(anonymous_5)",
        decl: {
          start: {
            line: 45,
            column: 35
          },
          end: {
            line: 45,
            column: 36
          }
        },
        loc: {
          start: {
            line: 45,
            column: 55
          },
          end: {
            line: 51,
            column: 1
          }
        },
        line: 45
      }
    },
    branchMap: {},
    s: {
      "0": 0,
      "1": 0,
      "2": 0,
      "3": 0,
      "4": 0,
      "5": 0,
      "6": 0,
      "7": 0,
      "8": 0,
      "9": 0,
      "10": 0,
      "11": 0,
      "12": 0
    },
    f: {
      "0": 0,
      "1": 0,
      "2": 0,
      "3": 0,
      "4": 0,
      "5": 0
    },
    b: {},
    _coverageSchema: "1a1c01bbd47fc00a2c39e90264f33305004495a9",
    hash: "4a5222b7c2cd06507f0e030663190b55ad977ebc"
  };
  var coverage = global[gcv] || (global[gcv] = {});
  if (!coverage[path] || coverage[path].hash !== hash) {
    coverage[path] = coverageData;
  }
  var actualCoverage = coverage[path];
  {
    // @ts-ignore
    cov_z33e6goor = function () {
      return actualCoverage;
    };
  }
  return actualCoverage;
}
cov_z33e6goor();
import axios from "axios";
import SessionToken from "@/features/SessionToken";
const BASE_LISTING_URL = (cov_z33e6goor().s[0]++, "http://localhost:8089/api/stat");
cov_z33e6goor().s[1]++;
export const deleteItemStats = async statDeleteFromFridgeDTO => {
  cov_z33e6goor().f[0]++;
  cov_z33e6goor().s[2]++;
  return await axios.post(`${BASE_LISTING_URL}/add/delete-item`, statDeleteFromFridgeDTO, {
    headers: {
      Authorization: `Bearer ${await SessionToken()}`
    }
  });
};
cov_z33e6goor().s[3]++;
export const addItemStats = async statAddItemToFridgeDTO => {
  cov_z33e6goor().f[1]++;
  cov_z33e6goor().s[4]++;
  return await axios.post(`${BASE_LISTING_URL}/add/bought-item`, statAddItemToFridgeDTO, {
    headers: {
      Authorization: `Bearer ${await SessionToken()}`
    }
  });
};
cov_z33e6goor().s[5]++;
export const getUserPercentageStats = async () => {
  cov_z33e6goor().f[2]++;
  cov_z33e6goor().s[6]++;
  return await axios.get(`${BASE_LISTING_URL}/get/user-stats/avg-thrown-per-day`, {
    headers: {
      Authorization: `Bearer ${await SessionToken()}`
    }
  });
};
cov_z33e6goor().s[7]++;
export const getUserMoneyStats = async () => {
  cov_z33e6goor().f[3]++;
  cov_z33e6goor().s[8]++;
  return await axios.get(`${BASE_LISTING_URL}/get/user-stats/money-wasted-per-day`, {
    headers: {
      Authorization: `Bearer ${await SessionToken()}`
    }
  });
};
cov_z33e6goor().s[9]++;
export const getFridgePercentageStats = async fridgeId => {
  cov_z33e6goor().f[4]++;
  cov_z33e6goor().s[10]++;
  return await axios.get(`${BASE_LISTING_URL}/get/fridge-stats/avg-thrown-per-day/${fridgeId}`, {
    headers: {
      Authorization: `Bearer ${await SessionToken()}`
    }
  });
};
cov_z33e6goor().s[11]++;
export const getFridgeMoneyStats = async fridgeId => {
  cov_z33e6goor().f[5]++;
  cov_z33e6goor().s[12]++;
  return await axios.get(`${BASE_LISTING_URL}/get/fridge-stats/money-wasted-per-day/${fridgeId}`, {
    headers: {
      Authorization: `Bearer ${await SessionToken()}`
    }
  });
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJuYW1lcyI6WyJjb3ZfejMzZTZnb29yIiwiYWN0dWFsQ292ZXJhZ2UiLCJheGlvcyIsIlNlc3Npb25Ub2tlbiIsIkJBU0VfTElTVElOR19VUkwiLCJzIiwiZGVsZXRlSXRlbVN0YXRzIiwic3RhdERlbGV0ZUZyb21GcmlkZ2VEVE8iLCJmIiwicG9zdCIsImhlYWRlcnMiLCJBdXRob3JpemF0aW9uIiwiYWRkSXRlbVN0YXRzIiwic3RhdEFkZEl0ZW1Ub0ZyaWRnZURUTyIsImdldFVzZXJQZXJjZW50YWdlU3RhdHMiLCJnZXQiLCJnZXRVc2VyTW9uZXlTdGF0cyIsImdldEZyaWRnZVBlcmNlbnRhZ2VTdGF0cyIsImZyaWRnZUlkIiwiZ2V0RnJpZGdlTW9uZXlTdGF0cyJdLCJzb3VyY2VzIjpbIlN0YXRzU2VydmljZS5qcyJdLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgYXhpb3MgZnJvbSBcImF4aW9zXCI7XHJcbmltcG9ydCBTZXNzaW9uVG9rZW4gZnJvbSBcIkAvZmVhdHVyZXMvU2Vzc2lvblRva2VuXCI7XHJcblxyXG5jb25zdCBCQVNFX0xJU1RJTkdfVVJMID0gXCJodHRwOi8vbG9jYWxob3N0OjgwODkvYXBpL3N0YXRcIjtcclxuZXhwb3J0IGNvbnN0IGRlbGV0ZUl0ZW1TdGF0cyA9IGFzeW5jIChzdGF0RGVsZXRlRnJvbUZyaWRnZURUTykgPT4ge1xyXG4gICAgcmV0dXJuIGF3YWl0IGF4aW9zLnBvc3QoYCR7QkFTRV9MSVNUSU5HX1VSTH0vYWRkL2RlbGV0ZS1pdGVtYCwgc3RhdERlbGV0ZUZyb21GcmlkZ2VEVE8sIHtcclxuICAgICAgICBoZWFkZXJzOiB7XHJcbiAgICAgICAgICAgIEF1dGhvcml6YXRpb246IGBCZWFyZXIgJHthd2FpdCBTZXNzaW9uVG9rZW4oKX1gLFxyXG4gICAgICAgIH0sXHJcbiAgICB9KTtcclxufTtcclxuXHJcbmV4cG9ydCBjb25zdCBhZGRJdGVtU3RhdHMgPSBhc3luYyAoc3RhdEFkZEl0ZW1Ub0ZyaWRnZURUTykgPT4ge1xyXG4gICAgcmV0dXJuIGF3YWl0IGF4aW9zLnBvc3QoYCR7QkFTRV9MSVNUSU5HX1VSTH0vYWRkL2JvdWdodC1pdGVtYCwgc3RhdEFkZEl0ZW1Ub0ZyaWRnZURUTywge1xyXG4gICAgICAgIGhlYWRlcnM6IHtcclxuICAgICAgICAgICAgQXV0aG9yaXphdGlvbjogYEJlYXJlciAke2F3YWl0IFNlc3Npb25Ub2tlbigpfWAsXHJcbiAgICAgICAgfSxcclxuICAgIH0pO1xyXG59O1xyXG5cclxuZXhwb3J0IGNvbnN0IGdldFVzZXJQZXJjZW50YWdlU3RhdHMgPSBhc3luYyAoKSA9PiB7XHJcbiAgICByZXR1cm4gYXdhaXQgYXhpb3MuZ2V0KGAke0JBU0VfTElTVElOR19VUkx9L2dldC91c2VyLXN0YXRzL2F2Zy10aHJvd24tcGVyLWRheWAsIHtcclxuICAgICAgICBoZWFkZXJzOiB7XHJcbiAgICAgICAgICAgIEF1dGhvcml6YXRpb246IGBCZWFyZXIgJHthd2FpdCBTZXNzaW9uVG9rZW4oKX1gLFxyXG4gICAgICAgIH0sXHJcbiAgICB9KTtcclxufTtcclxuXHJcbmV4cG9ydCBjb25zdCBnZXRVc2VyTW9uZXlTdGF0cyA9IGFzeW5jICgpID0+IHtcclxuICAgIHJldHVybiBhd2FpdCBheGlvcy5nZXQoYCR7QkFTRV9MSVNUSU5HX1VSTH0vZ2V0L3VzZXItc3RhdHMvbW9uZXktd2FzdGVkLXBlci1kYXlgLCB7XHJcbiAgICAgICAgaGVhZGVyczoge1xyXG4gICAgICAgICAgICBBdXRob3JpemF0aW9uOiBgQmVhcmVyICR7YXdhaXQgU2Vzc2lvblRva2VuKCl9YCxcclxuICAgICAgICB9LFxyXG4gICAgfSk7XHJcbn07XHJcblxyXG5leHBvcnQgY29uc3QgZ2V0RnJpZGdlUGVyY2VudGFnZVN0YXRzID0gYXN5bmMgKGZyaWRnZUlkKSA9PiB7XHJcbiAgICByZXR1cm4gYXdhaXQgYXhpb3MuZ2V0KGAke0JBU0VfTElTVElOR19VUkx9L2dldC9mcmlkZ2Utc3RhdHMvYXZnLXRocm93bi1wZXItZGF5LyR7ZnJpZGdlSWR9YCwge1xyXG4gICAgICAgIGhlYWRlcnM6IHtcclxuICAgICAgICAgICAgQXV0aG9yaXphdGlvbjogYEJlYXJlciAke2F3YWl0IFNlc3Npb25Ub2tlbigpfWAsXHJcbiAgICAgICAgfSxcclxuICAgIH0pO1xyXG59O1xyXG5cclxuZXhwb3J0IGNvbnN0IGdldEZyaWRnZU1vbmV5U3RhdHMgPSBhc3luYyAoZnJpZGdlSWQpID0+IHtcclxuICAgIHJldHVybiBhd2FpdCBheGlvcy5nZXQoYCR7QkFTRV9MSVNUSU5HX1VSTH0vZ2V0L2ZyaWRnZS1zdGF0cy9tb25leS13YXN0ZWQtcGVyLWRheS8ke2ZyaWRnZUlkfWAsIHtcclxuICAgICAgICBoZWFkZXJzOiB7XHJcbiAgICAgICAgICAgIEF1dGhvcml6YXRpb246IGBCZWFyZXIgJHthd2FpdCBTZXNzaW9uVG9rZW4oKX1gLFxyXG4gICAgICAgIH0sXHJcbiAgICB9KTtcclxufTtcclxuXHJcbiJdLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OztJQWVZO0lBQUFBLGFBQUEsWUFBQUEsQ0FBQTtNQUFBLE9BQUFDLGNBQUE7SUFBQTtFQUFBO0VBQUEsT0FBQUEsY0FBQTtBQUFBO0FBQUFELGFBQUE7QUFmWixPQUFPRSxLQUFLLE1BQU0sT0FBTztBQUN6QixPQUFPQyxZQUFZLE1BQU0seUJBQXlCO0FBRWxELE1BQU1D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