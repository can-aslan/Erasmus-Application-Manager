export const downloadBlobFile = (body: Blob, filenameWithoutExtension: string, extension = 'pdf') => {
    const aElement = document.createElement('a');
    const url = URL.createObjectURL(body)
    aElement.href = url
    aElement.download = 'image.png';
    document.body.appendChild(aElement);
    aElement.click();
    document.body.removeChild(aElement);
}