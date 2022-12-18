export const downloadBlobFile = (body: Blob, filenameWithoutExtension: string='image', extension = 'pdf') => {
    const aElement = document.createElement('a');
    const url = URL.createObjectURL(body)
    aElement.href = url
    aElement.download = filenameWithoutExtension;
    document.body.appendChild(aElement);
    aElement.click();
    document.body.removeChild(aElement);
}

export const downloadPdf = (data: string) => {
    const link = document.createElement('a');
    link.href = "data:application/octet-stream;base64," + data;
    link.download = 'file.pdf';
    link.click();
}