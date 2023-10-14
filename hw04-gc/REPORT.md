| Heap size           | Spend (before optimization) | Spend (after optimization) |
|---------------------|:---------------------------:|:--------------------------:|
| -Xms256m -Xmx256m   |     msec:33757, sec:33      |      msec:7343, sec:7      |
| -Xms288m -Xmx288m   |     msec:32840, sec:32      |      msec:4004, sec:4      |
| -Xms320m -Xmx320m   |     msec:32908, sec:32      |      msec:4565, sec:4      |
| -Xms416m -Xmx416m   |     msec:33934, sec:33      |      msec:5358, sec:5      |
| -Xms512m -Xmx512m   |     msec:31267, sec:31      |      msec:5589, sec:5      |
| -Xms1024m -Xmx1024m |     msec:26074, sec:26      |      msec:5751, sec:5      |
| -Xms1280m -Xmx1280m |     msec:25075, sec:25      |      msec:5819, sec:5      |
| -Xms1536m -Xmx1536m |     msec:25094, sec:25      |      msec:5025, sec:5      |
| -Xms1792m -Xmx1792m |     msec:25601, sec:25      |      msec:5573, sec:5      |
| -Xms2048m -Xmx2048m |     msec:25475, sec:25      |      msec:4801, sec:4      |

Оптимальный размер хипа - 288 Мб