# Getting Started

### Guides

* [Create a Pool](POST http://localhost:8080/thread-pools/create?name=fixed-worker&core=2&max=4&queue=50)
* [List Thread Pools](GET http://localhost:8080/thread-pools)
* [Resize a Pool](POST http://localhost:8080/thread-pools/custom-worker/resize?core=3&max=8)
* [Shutdown a Pool](DELETE http://localhost:8080/thread-pools/custom-worker)
* [Check Metrics](GET http://localhost:8080/actuator/prometheus)

