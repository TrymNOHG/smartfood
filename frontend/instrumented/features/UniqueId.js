function cov_4fwtqmymv() {
  var path = "C:\\Users\\Tomas\\systemutvikling\\idatt2106_2023_6\\frontend\\src\\features\\UniqueId.js";
  var hash = "d1043b1c61df372039ebd55e74cf391822233c6d";
  var global = new Function("return this")();
  var gcv = "__coverage__";
  var coverageData = {
    path: "C:\\Users\\Tomas\\systemutvikling\\idatt2106_2023_6\\frontend\\src\\features\\UniqueId.js",
    statementMap: {
      "0": {
        start: {
          line: 8,
          column: 15
        },
        end: {
          line: 8,
          column: 35
        }
      },
      "1": {
        start: {
          line: 9,
          column: 4
        },
        end: {
          line: 11,
          column: 5
        }
      },
      "2": {
        start: {
          line: 10,
          column: 8
        },
        end: {
          line: 10,
          column: 33
        }
      },
      "3": {
        start: {
          line: 12,
          column: 4
        },
        end: {
          line: 16,
          column: 6
        }
      },
      "4": {
        start: {
          line: 13,
          column: 23
        },
        end: {
          line: 13,
          column: 61
        }
      },
      "5": {
        start: {
          line: 14,
          column: 8
        },
        end: {
          line: 14,
          column: 18
        }
      },
      "6": {
        start: {
          line: 15,
          column: 8
        },
        end: {
          line: 15,
          column: 85
        }
      },
      "7": {
        start: {
          line: 20,
          column: 18
        },
        end: {
          line: 20,
          column: 38
        }
      },
      "8": {
        start: {
          line: 20,
          column: 24
        },
        end: {
          line: 20,
          column: 38
        }
      },
      "9": {
        start: {
          line: 22,
          column: 4
        },
        end: {
          line: 24,
          column: 5
        }
      }
    },
    fnMap: {
      "0": {
        name: "generateUUID",
        decl: {
          start: {
            line: 4,
            column: 9
          },
          end: {
            line: 4,
            column: 21
          }
        },
        loc: {
          start: {
            line: 4,
            column: 24
          },
          end: {
            line: 17,
            column: 1
          }
        },
        line: 4
      },
      "1": {
        name: "(anonymous_1)",
        decl: {
          start: {
            line: 12,
            column: 67
          },
          end: {
            line: 12,
            column: 68
          }
        },
        loc: {
          start: {
            line: 12,
            column: 93
          },
          end: {
            line: 16,
            column: 5
          }
        },
        line: 12
      },
      "2": {
        name: "UniqueID",
        decl: {
          start: {
            line: 19,
            column: 24
          },
          end: {
            line: 19,
            column: 32
          }
        },
        loc: {
          start: {
            line: 19,
            column: 35
          },
          end: {
            line: 25,
            column: 1
          }
        },
        line: 19
      },
      "3": {
        name: "(anonymous_3)",
        decl: {
          start: {
            line: 20,
            column: 18
          },
          end: {
            line: 20,
            column: 19
          }
        },
        loc: {
          start: {
            line: 20,
            column: 24
          },
          end: {
            line: 20,
            column: 38
          }
        },
        line: 20
      }
    },
    branchMap: {
      "0": {
        loc: {
          start: {
            line: 9,
            column: 4
          },
          end: {
            line: 11,
            column: 5
          }
        },
        type: "if",
        locations: [{
          start: {
            line: 9,
            column: 4
          },
          end: {
            line: 11,
            column: 5
          }
        }, {
          start: {
            line: 9,
            column: 4
          },
          end: {
            line: 11,
            column: 5
          }
        }],
        line: 9
      },
      "1": {
        loc: {
          start: {
            line: 9,
            column: 8
          },
          end: {
            line: 9,
            column: 83
          }
        },
        type: "binary-expr",
        locations: [{
          start: {
            line: 9,
            column: 8
          },
          end: {
            line: 9,
            column: 42
          }
        }, {
          start: {
            line: 9,
            column: 46
          },
          end: {
            line: 9,
            column: 83
          }
        }],
        line: 9
      },
      "2": {
        loc: {
          start: {
            line: 15,
            column: 16
          },
          end: {
            line: 15,
            column: 71
          }
        },
        type: "cond-expr",
        locations: [{
          start: {
            line: 15,
            column: 42
          },
          end: {
            line: 15,
            column: 48
          }
        }, {
          start: {
            line: 15,
            column: 52
          },
          end: {
            line: 15,
            column: 70
          }
        }],
        line: 15
      }
    },
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
      "9": 0
    },
    f: {
      "0": 0,
      "1": 0,
      "2": 0,
      "3": 0
    },
    b: {
      "0": [0, 0],
      "1": [0, 0],
      "2": [0, 0]
    },
    _coverageSchema: "1a1c01bbd47fc00a2c39e90264f33305004495a9",
    hash: "d1043b1c61df372039ebd55e74cf391822233c6d"
  };
  var coverage = global[gcv] || (global[gcv] = {});
  if (!coverage[path] || coverage[path].hash !== hash) {
    coverage[path] = coverageData;
  }
  var actualCoverage = coverage[path];
  {
    // @ts-ignore
    cov_4fwtqmymv = function () {
      return actualCoverage;
    };
  }
  return actualCoverage;
}
cov_4fwtqmymv();
/*
    Implementing using the UUID standard from RFC 4122, specifically under section 4.4.
 */
function generateUUID() {
  cov_4fwtqmymv().f[0]++;
  /*
    */
  let date = (cov_4fwtqmymv().s[0]++, new Date().getTime()); //Take the current timestamp
  cov_4fwtqmymv().s[1]++;
  if ((cov_4fwtqmymv().b[1][0]++, typeof performance !== 'undefined') && (cov_4fwtqmymv().b[1][1]++, typeof performance.now === 'function')) {
    cov_4fwtqmymv().b[0][0]++;
    cov_4fwtqmymv().s[2]++;
    //See whether high-precision timestamp can be used
    date += performance.now(); //Then use it
  } else {
    cov_4fwtqmymv().b[0][1]++;
  }
  cov_4fwtqmymv().s[3]++;
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (charPlaceHolder) {
    cov_4fwtqmymv().f[1]++;
    //Use the RFC 4122
    const random = (cov_4fwtqmymv().s[4]++, (date + (Math.random() << 4)) % 16 | 0); //Add randomness to the timestamp, making the UUID independent of it.
    cov_4fwtqmymv().s[5]++;
    date >>= 1; // Bit shifting the hex value one to the right.
    cov_4fwtqmymv().s[6]++;
    return (charPlaceHolder === 'x' ? (cov_4fwtqmymv().b[2][0]++, random) : (cov_4fwtqmymv().b[2][1]++, random & 0x3 | 0x8)).toString(16); //Changing the original string based on current value is x or y
  });
}

export default function UniqueID() {
  cov_4fwtqmymv().f[2]++;
  cov_4fwtqmymv().s[7]++;
  const getID = () => {
    cov_4fwtqmymv().f[3]++;
    cov_4fwtqmymv().s[8]++;
    return generateUUID();
  };
  cov_4fwtqmymv().s[9]++;
  return {
    getID
  };
}
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJuYW1lcyI6WyJjb3ZfNGZ3dHFteW12IiwiYWN0dWFsQ292ZXJhZ2UiLCJnZW5lcmF0ZVVVSUQiLCJmIiwiZGF0ZSIsInMiLCJEYXRlIiwiZ2V0VGltZSIsImIiLCJwZXJmb3JtYW5jZSIsIm5vdyIsInJlcGxhY2UiLCJjaGFyUGxhY2VIb2xkZXIiLCJyYW5kb20iLCJNYXRoIiwidG9TdHJpbmciLCJVbmlxdWVJRCIsImdldElEIl0sInNvdXJjZXMiOlsiVW5pcXVlSWQuanMiXSwic291cmNlc0NvbnRlbnQiOlsiLypcclxuICAgIEltcGxlbWVudGluZyB1c2luZyB0aGUgVVVJRCBzdGFuZGFyZCBmcm9tIFJGQyA0MTIyLCBzcGVjaWZpY2FsbHkgdW5kZXIgc2VjdGlvbiA0LjQuXHJcbiAqL1xyXG5mdW5jdGlvbiBnZW5lcmF0ZVVVSUQoKSB7XHJcbiAgICAvKlxyXG5cclxuICAgICovXHJcbiAgICBsZXQgZGF0ZSA9IG5ldyBEYXRlKCkuZ2V0VGltZSgpIC8vVGFrZSB0aGUgY3VycmVudCB0aW1lc3RhbXBcclxuICAgIGlmICh0eXBlb2YgcGVyZm9ybWFuY2UgIT09ICd1bmRlZmluZWQnICYmIHR5cGVvZiBwZXJmb3JtYW5jZS5ub3cgPT09ICdmdW5jdGlvbicpIHsgLy9TZWUgd2hldGhlciBoaWdoLXByZWNpc2lvbiB0aW1lc3RhbXAgY2FuIGJlIHVzZWRcclxuICAgICAgICBkYXRlICs9IHBlcmZvcm1hbmNlLm5vdygpIC8vVGhlbiB1c2UgaXRcclxuICAgIH1cclxuICAgIHJldHVybiAneHh4eHh4eHgteHh4eC00eHh4LXl4eHgteHh4eHh4eHh4eHh4Jy5yZXBsYWNlKC9beHldL2csIGZ1bmN0aW9uKGNoYXJQbGFjZUhvbGRlcikgeyAvL1VzZSB0aGUgUkZDIDQxMjJcclxuICAgICAgICBjb25zdCByYW5kb20gPSAoZGF0ZSArIChNYXRoLnJhbmRvbSgpIDw8IDQpKSAlIDE2IHwgMCAvL0FkZCByYW5kb21uZXNzIHRvIHRoZSB0aW1lc3RhbXAsIG1ha2luZyB0aGUgVVVJRCBpbmRlcGVuZGVudCBvZiBpdC5cclxuICAgICAgICBkYXRlID4+PSAxIC8vIEJpdCBzaGlmdGluZyB0aGUgaGV4IHZhbHVlIG9uZSB0byB0aGUgcmlnaHQuXHJcbiAgICAgICAgcmV0dXJuIChjaGFyUGxhY2VIb2xkZXIgPT09ICd4JyA/IHJhbmRvbSA6IChyYW5kb20gJiAweDMgfCAweDgpKS50b1N0cmluZygxNikgLy9DaGFuZ2luZyB0aGUgb3JpZ2luYWwgc3RyaW5nIGJhc2VkIG9uIGN1cnJlbnQgdmFsdWUgaXMgeCBvciB5XHJcbiAgICB9KVxyXG59XHJcblxyXG5leHBvcnQgZGVmYXVsdCBmdW5jdGlvbiBVbmlxdWVJRCgpIHtcclxuICAgIGNvbnN0IGdldElEID0gKCkgPT4gZ2VuZXJhdGVVVUlEKClcclxuXHJcbiAgICByZXR1cm4ge1xyXG4gICAgICAgIGdldElELFxyXG4gICAgfVxyXG59XHJcbiJdLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OztJQWVZO0lBQUFBLGFBQUEsWUFBQUEsQ0FBQTtNQUFBLE9BQUFDLGNBQUE7SUFBQTtFQUFBO0VBQUEsT0FBQUEsY0FBQTtBQUFBO0FBQUFELGFBQUE7QUFmWjtBQUNBO0FBQ0E7QUFDQSxTQUFTRSxZQUFZQSxDQUFBLEVBQUc7RUFBQUYsYUFBQSxHQUFBRyxDQUFBO0VBQ3BCO0FBQ0o7RUFFSSxJQUFJQyxJQUFJLElBQUFKLGFBQUEsR0FBQUssQ0FBQSxPQUFHLElBQUlDLElBQUksQ0FBQyxDQUFDLENBQUNDLE9BQU8sQ0FBQyxDQUFDLEdBQUM7RUFBQVAsYUFBQSxHQUFBSyxDQUFBO0VBQ2hDLElBQUksQ0FBQUwsYUFBQSxHQUFBUSxDQUFBLGlCQUFPQyxXQUFXLEtBQUssV0FBVyxNQUFBVCxhQUFBLEdBQUFRLENBQUEsVUFBSSxPQUFPQyxXQUFXLENBQUNDLEdBQUcsS0FBSyxVQUFVLEdBQUU7SUFBQVYsYUFBQSxHQUFBUSxDQUFBO0lBQUFSLGFBQUEsR0FBQUssQ0FBQTtJQUFFO0lBQy9FRCxJQUFJLElBQUlLLFdBQVcsQ0FBQ0MsR0FBRyxDQUFDLENBQUMsRUFBQztFQUM5QixDQUFDO0lBQUFWLGFBQUEsR0FBQVEsQ0FBQTtFQUFBO0VBQUFSLGFBQUEsR0FBQUssQ0FBQTtFQUNELE9BQU8sc0NBQXNDLENBQUNNLE9BQU8sQ0FBQyxPQUFPLEVBQUUsVUFBU0MsZUFBZSxFQUFFO0lBQUFaLGFBQUEsR0FBQUcsQ0FBQTtJQUFFO0lBQ3ZGLE1BQU1VLE1BQU0sSUFBQWIsYUFBQSxHQUFBSyxDQUFBLE9BQUcsQ0FBQ0QsSUFBSSxJQUFJVSxJQUFJLENBQUNELE1BQU0sQ0FBQyxDQUFDLElBQUksQ0FBQyxDQUFDLElBQUksRUFBRSxHQUFHLENBQUMsR0FBQztJQUFBYixhQUFBLEdBQUFLLENBQUE7SUFDdERELElBQUksS0FBSyxDQUFDLEVBQUM7SUFBQUosYUFBQSxHQUFBSyxDQUFBO0lBQ1gsT0FBTyxDQUFDTyxlQUFlLEtBQUssR0FBRyxJQUFBWixhQUFBLEdBQUFRLENBQUEsVUFBR0ssTUFBTSxLQUFBYixhQUFBLEdBQUFRLENBQUEsVUFBSUssTUFBTSxHQUFHLEdBQUcsR0FBRyxHQUFHLENBQUMsRUFBRUUsUUFBUSxDQUFDLEVBQUUsQ0FBQyxFQUFDO0VBQ2xGLENBQUMsQ0FBQztBQUNOOztBQUVBLGVBQWUsU0FBU0MsUUFBUUEsQ0FBQSxFQUFHO0VBQUFoQixhQUFBLEdBQUFHLENBQUE7RUFBQUgsYUFBQSxHQUFBSyxDQUFBO0VBQy9CLE1BQU1ZLEtBQUssR0FBR0EsQ0FBQSxLQUFNO0lBQUFqQixhQUFBLEdBQUFHLENBQUE7SUFBQUgsYUFBQSxHQUFBSyxDQUFBO0lBQUEsT0FBQUgsWUFBWSxDQUFDLENBQUM7RUFBRCxDQUFDO0VBQUFGLGFBQUEsR0FBQUssQ0FBQTtFQUVsQyxPQUFPO0lBQ0hZO0VBQ0osQ0FBQztBQUNMIn0=