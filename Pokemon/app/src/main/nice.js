(self.webpackChunk_N_E = self.webpackChunk_N_E || []).push([[180], {
    1110: function(W, l, d) {
        (window.__NEXT_P = window.__NEXT_P || []).push(["/assessment", function() {
            return d(205)
        }
        ])
    },
    205: function(W, l, d) {
        "use strict";
        d.r(l),
        d.d(l, {
            default: function() {
                return p
            }
        });
        var V = d(5893)
          , J = d(8764).lW;
        function Z(W, l) {
            let d = W.length / l
              , V = [];
            for (let l = 0; l < W.length; l += d)
                V.push(W.slice(l, l + d));
            let J = [];
            for (let W = 0; W < d; W++) {
                let l = [];
                for (let d = V.length - 1; d >= 0; d--) {
                    let J = V[d];
                    W < J.length && l.push(J[J.length - 1 - W])
                }
                J.push(l.join(""))
            }
            return J.join("").trim().split("").reverse().join("")
        }
        function N(W) {
            return J.from(W, "base64").toString("utf8")
        }
        var a = d(7294)
          , k = d(8764).lW;
        let R = {
            primeroCompleted: !1,
            segundoCompleted: !1,
            terceroCompleted: !1
        }
          , e = function W(l) {
            if (l < 10)
                return l;
            {
                let d = 0;
                for (; l > 0; )
                    d += l % 10,
                    l = Math.floor(l / 10);
                return W(d)
            }
        }(1687)
          , n = JSON.parse(N("ewogICAicHJpbWVybyI6IlNkSWNjWmFiWlljYVpkQ1paYmRkZFliYVphZEliWmJkWmNiYkxkYUlhZFZhWmNjWmFaWVlJWlZiYVliSWRMZFpjYmFDWWJkYllkYklJYkNJY1pZSUliY1paYWRJYmJjWllJY2NRYmJJY1paY2RDSVpJYlpJYVpaYlpiYWRkYmJJSUlZWlFaYkljWlliSWNaQ2JkY2FJYUxjYWNhSWJDY2NiYmJhSVliSWJRYlpaWWNJWmFaY2JRSWJkYmJMY2RiZVpjYUlaYmRZTGFjYklaWmJiWlpiZGVkWmJaUUliYWFJZVpaY1piVmFZYmRiYWNiYmFDYmJMSVlMSWRSSUlaYklZWmNkWkNJSWNjSWJjTElaQ2RiWklkZGNJWmJiYiBXR0dIeWlHV0NTR3kyR2ttWG1HV0NTR0NpV0FIbVMyQ2ltR21DWFdHV0EyQ1dteVdHeVhXSEdHM0NXR0hHQ0NXeXlXazJtR2lTWDNIR21rR3lXMkdIbW5uUUhXSG1tbUhXRzNuUzNtSG0yWG1Ia0dYRzNYRzJIQ0dRbVdHQzJuR0dHV1dTWDNHbUNTMkdtMmcyR2lHSFdDbUhpR0cyazNHbjJHSEcyV0htUzNTaVhtSFhHU21nbkhXMkMyQ3lDSFhHd0dIWFhDWENHaTJIR1FtMlMyaUdTR0NHUW5IM0NHR1NTWG1TM0dYMm1HaVh5aTJHa1czQ0hYQ0h3bUdIU21IWEd5Q1dsR0czbUdtbUNHV2tDeVdHQ0duR0N5R1QgNGhSUkJCVWx3QkZCNVZFeFJjaGR3QkZCQmRwTmRCbEJCbGxOQko1SmRvbEI1bEJGbEJJNWRVaFZCNVZCaEJCVkJCNUU5Vmx3QkpOUkpRbGxCRmdsZFJNSnBKZGRSYzlNNTFSa0JWVlI5Z1I1a2wxUkZSSUVsSkJGcFE0aEIxUWxsTjVRQlJKWjF3QjVGRlZwZGhCVk41QmxSQkZGNUVsaGtZbFFFOWxSVUJKQkJSVVJrRkI5b1ZSVWxCMUJCQmRNOXBWUmtWQkpCVkI1VjlwUTVCRndoQkZCOW9WUlZCVjFCQkJGQmNoSTlWbEJaQkIxOUVWSkJOSkJabzlsUkJRZFE5QkJGUkpFQnNFVjlCUkZKQkJWOUJoTUZCQlY4IGdsbG92MGd1Z3p5cHB6Z2xwZ3ZvZ216dnNvQnBzd3V2aXNobGl1bnlvSzAwa3N5anVtZ2twS3luMG5raHpwenIwbWtndWp2Z3d3bHZwS3UwenlzMGhsZ2xVdm9wcGdoZ2s1bEtxeTVvMWdscEswbHp1b3NndWxtdEJnZ2h0bHMwem9uS3UzcnZsZ2lreXVrVWxsMDVvbGlubDB1c2xndHZnZ25zZ3pqdkttanZ1MXNvZzJuM0swcGdzamxocGhoZ2xYdW9nencwMHBpa3V1QmdqaHBnbHp1aHVLMG9uMDVoaXpoMHVLbGd1anZzbHZ6bDNndDVod3Jodkt5dW9sc29nbHB0dXZsZ2hzZzF1aHl0MTB6cjF2bHN1MG5oPSAiLAogICAic2VndW5kbyI6IlNkSVpJWWJiWmNiTFpjWUlaSWNiYWFaWlZkZGNhWmNiZFpiY1lDY2RaSVlMYlpaY1pDY2JiZFpiSWRjSWRUWmJhY0lkYmNiYlpkVmJaYlpiWlpJYkNkWVlaY2JkWWJJY2FDWWNjWllMSWRhZEliTEljYlllYWVZY1pZYkxaWWJlZGFhZGJZZExiWUljYlpiSWNZYlpDWmJaWmRJZFlaWllaUVlkWmJJWmFJZFpjYmJRSUxhSWFjTFlaYlpWZFpaYWJaSUlaYkxRSWFMWllJY2RiYkxJWVpiWUliYWJkYkllVmRkYkliTGJaWmRkWUNaWmRkSVllWmFJY1ljWkNJZGNZWmRaWWJiWlNjY2FJWmNaZFpJZFpRSWJhWlliSWJkSVlDSWRJSVpjY1phSWIgV0dHV0cybTJYM21BU21uRzNHMkdXWFdTR0NYeUcybjJHU20zMmtHQ2lHV0NuV1dtU2tpM1dIbUdISG1IQ0dTRzNtSEdtbVMzbkMyV1NHeWlYV0htbEdTWFhHaTIybUhteWsyM0NTV0NHR1dHSDNBSG4yU1dHU1NtQ1gzQVNYMm0yQ0cyR1hXQW1XR2kyU3lHMnkzQ2xTbVhTV0hHU1dYMlNTV1dTbUhTWEdtWGltaVdIQ1dHV21DV1NHeVcyU2lHbVhHSFMyZ25IV0NTWEdHQ3lHQUdXU3lXR1d5aVgzSFNHM0czSDNDeVdTWEgya0NHR0dHWFNpR0cyeUdTbEdDeVdTWEcyMkdTVzNDWEhTbUNXV0dtU1dIM0NTWG1HM0dIV2xIWEdIU21paUdHbSA0aGRGMWhVWWhSTnBCVVZOTUUxdzVONXdoQkpCVVZNWWxCUUJVRUZCQmQ1Qk40MUY0OUJaVk05OUJSNU53bEI5ZHNkZzg5QklKd1U5QjlCQkpOTk1kZ0JKSXh3VUY5Tmw0VmdSQkJ0QjVVNGhOSnBCUVlCMTl3QkZCQkpwQko1VWxCVTlRSjVwUVI5QmxCQk4xQlY0UkJsSkIxZGdCTlE5d0I1RkJ4UkJOaFVaQjl3NWRCdE54VkI1Qjl3NUZCQlU5TTlSQjVvVk54QkJaRUZCQkZwTjVCQnRFRkJCSWRkdzhKVVZOSkJCRkJJSlVGQlE4aEpWQkJsTjFCRjROeEJCNUJJRlVZbHc0UkJSUkJGdzVWUmx3NVJWQkJrOXRjaEI0ZFJObFJCUkJCbHhRIGdseTBocGdncGxsWGhnMHZzZ2hnejBqS2gwdTBnaGdndGhnaHNneXZoeWsxbGdpaksxdGx1Z3MzaGx6bEtyamp2c3BnZ3ZtZ2xLZzJodXB3bTA1c3Bnc25naGdndTBvdUtoZ2wzMGxodmdnbGpsQmhnZ3p3dWdudXl3MFhvdHBnMDBneWd2a0JnazEyajBodnB6dUtvMTJ6b3pwZ3p5Z2tLc25udjVvM2xoZ2xyM0trbHNsb2t1a2podUtoeXYwZzBzeW96bkswMHMzb2xneTB3NUJvajB0bGd5cHZndWhLZ3BneTA1MHMydmdoc3VoZ2dsbDB2MHp2cHpqS3ZsMWtqdmd1Z2dtS2dsM29vbnVnenVsaktrb24zdGcwdWdsc3NseTB1b3ZsdjB6aHUgIiwKICAgInRlcmNlcm8iOiJTWWJaYUpZWklaYkxhWmFjY2NjWUliSVpiQ1pJYmFZSVpiYVpkWlFJYmFjY1pZY2FiQ2FhYWJjYmNiZFpRSUliWmRkSVphYUxkZGRkSWNjYkljYmNiWlFJYmFMYlpaWUlJYlpDSWVhSWJJSWRJWkNaWmRJY0lJWlpaSVpUSWJjYWNiSWJJYWRWYWRlYVpJWmRlYWRDSWFJZGJaZGFkZWFaTGJiY1lJYmRhZElZYUNjYWNJWWNiSWNZSWNDZFpiY2JhYmFJSWNDWmJJWmJaYklkWUNaWmRJWWViWUllVmRaZFpkWlpJWmJkYlpWYVpkWUliSVlhWlpMUUlJSWRkYmFhSWRaWUNJYWJJY0xhSWRiWmNaVmNiSWJJWmNJSVlDWmRkZGRZWUliSWJDWmJaYUlJYWRiYkljYmJjZFphY2NjWmFaYWJJWmRaWmJjSWRjZGJZSWFaUWRDY1phWWJjYllJWmFkSWJkYWJaWllhWWNkSVpjWmRhYVpiYU9TZUpJY2FJY2FjZGNaYVpjWmNaSWJkY2JJSWRiWmRaYWJJY2FJSUxJSWJjZGFJZFlJTFphZFliWWRZYmJJZGRJSWRZYUlkWWNaSWJjSWJaZFphSWRiYmFiSVlhSVpjZGFMZVpkZFphWmNZSVpZTFNlSkljYUljYWNaWVphWmNaY1pJYmRjYklJZGJaZFpJWWRhYlpiYllJYmNaWVlaWWRaYmJiWUlhSWNZSVlDYWRJSWRZYmNkTFphZFliWVpjZWNkSWNiY1phSWRZSUljYmNhYkNkWWNiZGFZYWRZYkxWWUliYlpJZGJhY2RaTGJkYllJZFliSVpiSWNjZGFjWmNJZCBXUzNDRzNuMkdDMkFHU1d5bTJuV0dtR1NHbFhIM0hXR1dtV1gyU1dHblcyM1hXeVhtZ0dHWGl5R2ltMlNXSEdpVzJDR1hXV0FYR1dIR2ltWEdpbWkyU1dIRzJDR25TV0dHMlNsR1NYSG1IR21HWGtDV0dIbUdHR21XR1MzSG55R0MzR21HV0MyQ21TRzJHQ21TV0NsR1dHeTNIMkNtU0cyQW1HbVdHMzJHQ0dYSGtpV3lIWG1tSG1XSDNsR1dHeTNXR1dIRzNnU21IUzNDaUd5WGtDV0dHWFNpV0dTMldYR0NYV1hHQ0cybUMyQ1dIV0dtSFhXWFdnV0hIR21HM0NXSEczMmtIWG1IbUNHR3kzQ0dTMm0zR21HU21ISFdrQ1hHV0gzWEdtR1dnaTNDV0hHV1gyM0czMjIybXlHRzJ5aVdXV21HQ1dHeTIzR0dIR0dXR1dXVUdtR25IV0dtbVdHV1hISG1HbmtYV1NTU21XSFcyWDJDR21HV2dXVzNHR1dHaUcySG1XV1htUzJDR0dDR25HR3kzQ1dDR21IbVdHRjFIQW5tQ1hHbTJFaVNXR25HU1dXbkdHeTJHR0dXV0hHMkhYR25tRzNDV1dXQVdpMlgyR1hXR0NtR1hDU1dtR3lHSG1XSG5YZ1dXM0dHV0dpR21XMldXWG1TMkNHR0NHbkdHeTNDV0NHVzJ5eVdYR1dBbm5YU1hTV1dTMkMzV0dHR0hXSFhtR21HR1dXbm1XaVNXR25HU1dtUzNHR2lubXlHR20yRUgzWG1XSG1DMm1tR0hXV0gzMmcyWEcybW1HSDNXbkdXQ1dHaVdHR1gyQVMyR25tR0dteW1IUyA0QkpCRk1KaEZCeHBWQjVCVWhBNTFSRkJRZEpSVlE1WnhjNUpsdzVOTjk1TWg1QlpVcFU5cEJCVkJRbHc1ZE5CVWhkUkE1UnBJaGRNOUJWTTlCNUJsd3hWOVZCd0p3eEpObDRkWkJSZGRkNVZ0Y0ZCVWhkeGxFbFY1aHdWTk5CRndWMVJGZHdsQlZCOWhGQlZCZDRkSng1QkpNbEJWQjlocFFWNDVkY2w5QlpWUTlCNUJCbDlRQlU1WlFkZzVWQk5KbFZSeFFwQjlSQkpCQjVCa0ZCVWhKVkJCTlJ3VTVKRkJKeE1GQndVVndsQkZNNTFSUlE1SlJvNU5kMVVoVkJaZGdKVWxSTVZkeEJsNTFWQkZ3aFVWMVJGQlZSSjBGQkloZE1KUkVWaFVwQlVCdFJwNE00VjFONFk5bEJVaGxCQTV4ZE5GQmxsQkpRbFZKRlU1RmRRazhGeGtWNFY1YzVORlprTlE4VkJKSjVBQjlOTk1oUWxCVTk5NW9ZOUpGeDVaQlU5ZFU1NUk5d1ZBSnhBOVI5aEJVQmxCVVZCRmQ5VlZScFJGQlJGRlZGQkIwOFZRQjUxUng1QUY5bFZONWRnOVZKTlJWRlZCZDU1cDFCZFI0eEo1RkJWbFJCQmxWbEFGZFU1TlJKb1k5SkZ4NVpCVVZGZzU1STl3VkFKeEE5UjloQlVCbEJaMTlCQk5CVTVwUkJRQkpCNUZCUndJNTlWRkpOUlFGbFZGVmx4UVZ4QkIwOFZRQk5sQWxWWkJObEJGRkZWRk5STVZGa0ZCTlJjOFY0NUpSNW9VSnh0YzlWSko1a0Y1QkpsdzUxbFI0cEI5WmRROFZsQjlsNCBnM3MwMGdwMHVpa1h5dGtoZ2hna3B6eXZzb2xvbmdrbHBnMDB1S2t2ajFsZ3dra3B1VWd5dnBqaGhna0trbGh6Z2h6bGd6bFBndm9zMWtoczFwbDJqS3N1amtoZ2xnczVvaktsczNvcHpsbHl1c3V6Z2x2a3VnbXkwMUt5bGx6eWd5cHpzb0swbHkwMTB1bHl6b0tsMWtsM3NnMGx5MDEwQmdoZ2t5ZzAxaHluczF0a2hzbjFzMWdraHNwZ2t6d3pwMHp2aHVYcjNvM3NwaGwzc3V6Z2xsMHBsb2hLZ2t6dXZ6MnN1aGdnbEswb3lna3B6b2cwamxLa3Zsdmd5bnNscGdoc3VvZzN2azB6bG11empLbGd5cHp5bWx2dnN1dmd2b2dsbGczdnVKNW5zbHZ2Z2dneXBwZ2dzdTBnNWp2SzBzbGx1aXN1eTFzdXlsaWdrc3VnZ2d0cGd0Z2hwZ2t5MHBzbGdnaDIweWhLd2swd2dsZzAwZ3MzbktnMWx3NW52MGdtaGdubGdzZ3V6MWxLcHptdjVuaXMwZzNoa3RtSll2cGxqM29rdWtKWGhnZ3BnbWtsaDVsSzVtdXkwbnBndDB6bGxreXVodDBub2hqdXBzbHVudWpoMjVpeXV1dUt5aGdrdjNsS2cxbHc1bnYwZ3p5Z25sZ3NndXoxbEtwem12NW5pc2h5bHkwa3ZwZ2twbHlnc25zbm50bGdnNTB5d3Zvb2dqbHp1eDJsZ3owWGhnZ3BnejEwS3p0dmwxdTAwa3VrSjVsZ3NpZ2podnBnZ3RnejFwektnbHZwZ3k0aGtoZ3MwaHB2Z2t2MnB1WHNydmhnZ2h1bXR2PSAiCn0="))
          , t = "T25seSBnaG9zdHMgY2FuIHBhc3MgdGhyb3VnaCB3YWxscyE=";
        Object.defineProperty(window, JSON.parse(Z(N("e20gZ30iZSJpIG4iYm4gYTplIiA="), e)).name, {
            get: ()=>{
                console.log(Z(n.primero, e)),
                R.primeroCompleted = !0
            }
        }),
        Object.defineProperty(window, JSON.parse(Z(N("e20gdSIiZSJyfW4ibm8gYTplbiA="), e)).name, {
            get: ()=>{
                if (!R.primeroCompleted)
                    throw Error(t);
                console.log(Z(n.segundo, e)),
                R.segundoCompleted = !0
            }
        });
        let Y = W=>{
            let {content: l} = W;
            return (0,
            V.jsxs)("div", {
                className: "bg-primary-900 rounded-lg my-4 shadow-lg max-w-4xl mx-auto shadow-black/40",
                children: [(0,
                V.jsx)("div", {
                    className: "p-4",
                    children: (0,
                    V.jsxs)("div", {
                        className: "flex justify-between items-center w-[55px]",
                        children: [(0,
                        V.jsx)("div", {
                            className: "bg-red-500 w-3 h-3 rounded-full"
                        }), (0,
                        V.jsx)("div", {
                            className: "bg-yellow-500 w-3 h-3 rounded-full"
                        }), (0,
                        V.jsx)("div", {
                            className: "bg-green-500 w-3 h-3 rounded-full"
                        })]
                    })
                }), (0,
                V.jsx)("div", {
                    className: "bg-primary-800 py-2 px-4 overflow-hidden min-h-[200px] rounded-b-lg",
                    children: l ? (0,
                    V.jsx)("div", {
                        className: "text-white font-mono",
                        children: (0,
                        V.jsx)("pre", {
                            children: l
                        })
                    }) : (0,
                    V.jsxs)(V.Fragment, {
                        children: [(0,
                        V.jsx)("p", {
                            className: "text-white font-mono",
                            children: "guest@localhost:~$"
                        }), (0,
                        V.jsx)("p", {
                            className: "text-white font-mono",
                            children: "$ begin"
                        })]
                    })
                })]
            })
        }
        ;
        function p() {
            let[W,l] = (0,
            a.useState)(void 0);
            return window.backpropagation = ()=>{
                if (!R.primeroCompleted || !R.segundoCompleted)
                    throw Error(t);
                l(k.from(Z(n.tercero, e), "base64").toString("utf-8"))
            }
            ,
            (0,
            V.jsx)(Y, {
                content: W
            })
        }
    }
}, function(W) {
    W.O(0, [764, 774, 888, 179], function() {
        return W(W.s = 1110)
    }),
    _N_E = W.O()
}
]);