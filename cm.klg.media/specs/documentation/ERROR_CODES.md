# Error Codes

## Media Module

| Type                    | HTTP Code | Business Code   | Description                            | Exception                               |
|-------------------------|-----------|-----------------|----------------------------------------|-----------------------------------------|
| `Bad Request`           | `400`     | `MEDIA-400-001` | Document Size Limit Exceeded           | `DocumentSizeLimitExceededException`    |
| `Bad Request`           | `400`     | `MEDIA-400-002` | Document Size Cannot Be Negative       | `DocumentSizeCannotBeNegativeException` |
| `Bad Request`           | `400`     | `MEDIA-400-003` | Document Name Cannot Be Blank          | `DocumentNameCannotBeBlankException`    |
|                         |           | `               |                                        |                                         |
|                         |           | `               |                                        |                                         |
| `Not Found`             | `404`     | `MEDIA-404-001` | Document Not Found                     | `DocumentNotFoundException`             |
|                         |           | `               |                                        |                                         |
|                         |           | `               |                                        |                                         |
| `Internal Server Error` | `500`     | `MEDIA-500-001` | Fail to Store Document                 | `FailToStoreDocumentException`          |
| `Internal Server Error` | `500`     | `MEDIA-500-002` | Cannot Read Document File From Request | `CannotReadDocumentException`           |
| `Internal Server Error` | `500`     | `MEDIA-500-003` | Fail to Load Document From Storage     | `FailToLoadDocumentException`           |