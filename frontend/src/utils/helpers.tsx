export const downloadBlobFile = (body: Uint8Array[], filenameWithoutExtension: string, extension = 'pdf') => {
    const blob = new Blob(body)
    const filename = `${filenameWithoutExtension}.${extension}`

    const link = document.createElement('a')
    const url = URL.createObjectURL(blob)
    link.setAttribute('href', url)
    link.setAttribute('download', filename)
    link.style.visibility = 'hidden'
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
}